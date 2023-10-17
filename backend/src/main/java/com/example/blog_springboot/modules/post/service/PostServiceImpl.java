package com.example.blog_springboot.modules.post.service;

import com.example.blog_springboot.commons.Constants;
import com.example.blog_springboot.commons.PagingResponse;
import com.example.blog_springboot.commons.SuccessResponse;
import com.example.blog_springboot.modules.follow.model.Follow;
import com.example.blog_springboot.modules.follow.repository.FollowRepository;
import com.example.blog_springboot.modules.notification.dto.CreateNotificationDTO;
import com.example.blog_springboot.modules.notification.service.NotificationService;
import com.example.blog_springboot.modules.notification.service.UserNotificationService;
import com.example.blog_springboot.modules.notification.viewmodel.NotificationVm;
import com.example.blog_springboot.modules.post.constant.PostConstants;
import com.example.blog_springboot.modules.post.dto.CreatePostDTO;
import com.example.blog_springboot.modules.post.dto.UpdatePostDTO;
import com.example.blog_springboot.modules.post.dto.UpdatePostStatusDTO;
import com.example.blog_springboot.modules.post.exception.*;
import com.example.blog_springboot.modules.post.model.Post;
import com.example.blog_springboot.modules.post.repository.PostRepository;
import com.example.blog_springboot.modules.post.viewmodel.PostListVm;
import com.example.blog_springboot.modules.post.viewmodel.PostVm;
import com.example.blog_springboot.modules.series.constant.SeriesConstants;
import com.example.blog_springboot.modules.series.exception.NotAuthorSeriesException;
import com.example.blog_springboot.modules.series.exception.SeriesNotFoundException;
import com.example.blog_springboot.modules.series.repository.SeriesRepository;
import com.example.blog_springboot.modules.tag.dto.CreateTagDTO;
import com.example.blog_springboot.modules.tag.model.Tag;
import com.example.blog_springboot.modules.tag.repository.TagRepository;
import com.example.blog_springboot.modules.tag.serivce.TagService;
import com.example.blog_springboot.modules.tag.viewmodel.TagVm;
import com.example.blog_springboot.modules.user.enums.Role;
import com.example.blog_springboot.modules.user.model.User;
import com.example.blog_springboot.modules.user.viewmodel.UserDetailVm;
import com.example.blog_springboot.modules.websocket.service.WebSocketService;
import com.example.blog_springboot.utils.Utilities;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final SeriesRepository seriesRepository;
    private final TagRepository tagRepository;
    private final TagService tagService;
    private final NotificationService notificationService;

    private final UserNotificationService userNotificationService;

    private final WebSocketService webSocketService;

    private final FollowRepository followRepository;


    public PostServiceImpl(
            PostRepository postRepository,
            SeriesRepository seriesRepository,
            TagRepository tagRepository,
            TagService tagService,
            NotificationService notificationService,
            UserNotificationService userNotificationService,
            WebSocketService webSocketService,
            FollowRepository followRepository
    ){
        this.postRepository = postRepository;
        this.seriesRepository = seriesRepository;
        this.tagRepository = tagRepository;
        this.tagService = tagService;
        this.notificationService = notificationService;
        this.userNotificationService = userNotificationService;
        this.webSocketService = webSocketService;
        this.followRepository = followRepository;
    }

    @Override
    public SuccessResponse<PagingResponse<List<PostListVm>>> getAllPost(String sortBy, int pageIndex) {
        Pageable paging = PageRequest.of(pageIndex, Constants.PAGE_SIZE, Sort.by(sortBy));

        Page<Post> pagingResult = postRepository.findAllByPublished(true,paging);

        List<PostListVm> listPostVm = pagingResult.stream().map(Utilities::getPostListVm).toList();

        return new SuccessResponse<>("Thành công",new PagingResponse<>(pagingResult.getTotalPages(),(int)pagingResult.getTotalElements(),listPostVm));
    }

    @Override
    @Transactional
    public SuccessResponse<PostListVm> createPost(CreatePostDTO dto, User userPrincipal) throws JsonProcessingException {
        if(dto.getListTags().size() > 3){
            throw new MaxTagException(PostConstants.MAX_TAG);
        }

        var foundPostBySlug = postRepository.findBySlug(dto.getSlug()).orElse(null);
        if(foundPostBySlug != null){
            throw new PostSlugDuplicateException(PostConstants.POST_SLUG_DUPLICATE);
        }

        List<Tag> listTag = createListTag(dto.getListTags());

        var newPost = new Post();
        newPost.setTitle(dto.getTitle());
        newPost.setContent(dto.getContent());
        newPost.setSlug(dto.getSlug());
        newPost.setThumbnail(dto.getThumbnail());
        newPost.setSummary(dto.getSummary());
        newPost.setUser(userPrincipal);
        newPost.setPublished(false);
        newPost.setCreatedAt(new Date());
        newPost.setUpdatedAt(new Date());
        newPost.setTags(listTag);
        newPost.setTotalView(0);

        if(dto.getSeriesId() != null){
            var foundSeries = seriesRepository.findById(dto.getSeriesId()).orElse(null);
            if(foundSeries == null){
                throw new SeriesNotFoundException(SeriesConstants.SERIES_NOT_FOUND);
            }
            newPost.setSeries(foundSeries);
        }

        var savePost = postRepository.save(newPost);
        if(savePost == null){
            throw new CreatePostException(PostConstants.CREATE_POST_FAILED);
        }

        // sau khi khởi tạo post thành công thì tạo ra 1 thông báo
        CreateNotificationDTO notification = new CreateNotificationDTO();
        notification.setLink("");
        notification.setMessage("");

        var newNotification = notificationService.createNotification(notification);

        // sau đó nối thông báo với user
        List<User> users = followRepository.getListFollowersByUser(userPrincipal);

        userNotificationService.createUserNotification(users,newNotification);

        // Tạo ra 1 notification view model và gửi tới client
        NotificationVm notificationVm = new NotificationVm();
        notificationVm.setId(newNotification.getId());
        notificationVm.setLink(newNotification.getLink());
        notificationVm.setMessage(newNotification.getMessage());
        notificationVm.setRead(false);

        webSocketService.sendNotificationToClient(users,notificationVm);

        var newPostListVm = Utilities.getPostListVm(savePost);
        return new SuccessResponse<>(PostConstants.CREATE_POST_SUCCESS,newPostListVm);
    }

    @Override
    @Transactional
    public SuccessResponse<PostListVm> updatePost(UpdatePostDTO dto,int id, User userPrincipal) {
        if(!(userPrincipal.getRole() == Role.ADMIN)){
            var isAuthor = postRepository.existsByUserAndId(userPrincipal,id);
            if(!isAuthor){
                throw new NotAuthorPostException(PostConstants.NOT_AUTHOR_POST);
            }
        }

        if(dto.getListTags().size() > 3){
            throw new MaxTagException(PostConstants.MAX_TAG);
        }

        var foundPost = postRepository.findById(id).orElse(null);
        if(foundPost == null){
            throw new PostNotFoundException(PostConstants.POST_NOT_FOUND);
        }

        var foundPostBySlug = postRepository.findBySlug(dto.getSlug()).orElse(null);
        if(foundPostBySlug != null && foundPostBySlug != foundPost){
            throw new PostSlugDuplicateException(PostConstants.POST_SLUG_DUPLICATE);
        }

        foundPost.setTitle(dto.getTitle());
        foundPost.setContent(dto.getContent());
        foundPost.setSlug(dto.getSlug());
        foundPost.setSummary(dto.getSummary());
        foundPost.setUpdatedAt(new Date());
        foundPost.setPublished(dto.isPublished());

        if(dto.getThumbnail() != null){
            foundPost.setThumbnail(dto.getThumbnail());
        }

        // new tag update
        List<Tag> addTags = createListTag(dto.getListTags());

        foundPost.removeAllTag(foundPost.getTags());
        foundPost.addAllTag(addTags);

        var savePost = postRepository.save(foundPost);
        if(savePost == null){
            throw new UpdatePostException(PostConstants.UPDATE_POST_FAILED);
        }

        var postListVm = Utilities.getPostListVm(savePost);

        return new SuccessResponse<>(PostConstants.UPDATE_POST_SUCCESS,postListVm);
    }

    @Override
    public SuccessResponse<Boolean> deletePost(int id, User userPrincipal) {
        if(!(userPrincipal.getRole() == Role.ADMIN)){
            var isAuthor = postRepository.existsByUserAndId(userPrincipal,id);
            if(!isAuthor){
                throw new NotAuthorPostException(PostConstants.NOT_AUTHOR_POST);
            }
        }

        var foundPost = postRepository.findById(id).orElse(null);
        if(foundPost == null){
            throw new NotAuthorPostException(PostConstants.NOT_AUTHOR_POST);
        }

        postRepository.delete(foundPost);

        return new SuccessResponse<>(PostConstants.DELETE_POST_SUCCESS,true);

    }

    @Override
    public SuccessResponse<PostVm> getPostBySlug(String slug) {
        var foundPost = postRepository.getPostBySlug(slug).orElse(null);
        if(foundPost == null){
            throw new PostNotFoundException(PostConstants.POST_NOT_FOUND);
        }

        var postVm = Utilities.getPostVm(foundPost);

        return new SuccessResponse<>("Thành công",postVm);

    }

    @Override
    public SuccessResponse<PagingResponse<List<PostListVm>>> getAllPostAuthor(String username, String sortBy, int pageIndex) {
        Pageable paging = PageRequest.of(pageIndex,Constants.PAGE_SIZE,Sort.by(sortBy));

        Page<Post> pagingResult = postRepository.getAllPostByUsername(username,paging);

        List<PostListVm> listPostVm = pagingResult.stream().map(Utilities::getPostListVm).toList();

        return new SuccessResponse<>("Thành công",new PagingResponse<>(pagingResult.getTotalPages(),(int)pagingResult.getTotalElements(),listPostVm));
    }

    @Override
    public SuccessResponse<PagingResponse<List<PostListVm>>> getAllPostNotPublished(String sortBy, int pageIndex) {
        Pageable paging = PageRequest.of(pageIndex,Constants.PAGE_SIZE,Sort.by(sortBy));

        Page<Post> pagingResult = postRepository.getAllPostNotPublished(paging);

        List<PostListVm> listPostVm = pagingResult.stream().map(Utilities::getPostListVm).toList();

        return new SuccessResponse<>("Thành công",new PagingResponse<>(pagingResult.getTotalPages(),(int)pagingResult.getTotalElements(),listPostVm));
    }

    @Override
    public SuccessResponse<PagingResponse<List<PostListVm>>> getAllByCurrentUser(User user, String sortBy, int pageIndex) {
        Pageable paging = PageRequest.of(pageIndex,Constants.PAGE_SIZE,Sort.by(sortBy));

        Page<Post> pagingResult = postRepository.getAllPostByCurrentUser(user,paging);

        List<PostListVm> listPostVm = pagingResult.stream().map(Utilities::getPostListVm).toList();

        return new SuccessResponse<>("Thành công",new PagingResponse<>(pagingResult.getTotalPages(),(int)pagingResult.getTotalElements(),listPostVm));
    }

    @Override
    public SuccessResponse<Boolean> updateStatus(int id, User userPrincipal, UpdatePostStatusDTO dto) {
        if(!(userPrincipal.getRole() == Role.ADMIN)){
            var isAuthor = postRepository.existsByUserAndId(userPrincipal,id);
            if(!isAuthor){
                throw new NotAuthorPostException(PostConstants.NOT_AUTHOR_POST);
            }
        }

        var foundPost = postRepository.findById(id).orElse(null);
        if(foundPost == null){
            throw new NotAuthorPostException(PostConstants.NOT_AUTHOR_POST);
        }

        foundPost.setPublished(dto.isStatus());
        var savePost = postRepository.save(foundPost);
        if(savePost == null){
            throw new UpdatePostException(PostConstants.CHANGE_POST_STATUS_FAILED);
        }

        return new SuccessResponse<>(PostConstants.CHANGE_POST_STATUS_SUCCESS,true);
    }

    @Override
    public SuccessResponse<Boolean> addPostToSeries(int postId, int seriesId, User userPrincipal) {
        if(!(userPrincipal.getRole() == Role.ADMIN)){
            var isPostAuthor = postRepository.existsByUserAndId(userPrincipal,postId);
            if(!isPostAuthor){
                throw new NotAuthorPostException(PostConstants.NOT_AUTHOR_POST);
            }

            var isSeriesAuthor = seriesRepository.existsByUserAndId(userPrincipal,seriesId);
            if(!isSeriesAuthor){
                throw new NotAuthorSeriesException(SeriesConstants.NOT_AUTHOR_SERIES);
            }
        }

        var foundPost = postRepository.findById(postId).orElse(null);
        if(foundPost == null){
            throw new PostNotFoundException(PostConstants.POST_NOT_FOUND);
        }

        var foundSeries = seriesRepository.findById(seriesId).orElse(null);
        if(foundSeries == null){
            throw new SeriesNotFoundException(SeriesConstants.SERIES_NOT_FOUND);
        }

        foundPost.setSeries(foundSeries);

        var savePost = postRepository.save(foundPost);

        if(savePost == null){
            throw new UpdatePostException(PostConstants.ADD_POST_SERIES_FAILED);
        }

        return new SuccessResponse<>(PostConstants.ADD_POST_SERIES_SUCCESS,true);

    }

    @Override
    public SuccessResponse<PagingResponse<List<PostListVm>>> getAllPostByTag(String tagSlug, String sortBy, int pageIndex) {
        Pageable paging = PageRequest.of(pageIndex,Constants.PAGE_SIZE,Sort.by(sortBy));

        Page<Post> pagingResult = postRepository.getPostByTagSlug(tagSlug,paging);

        var postListVm = pagingResult.stream().map(Utilities::getPostListVm).toList();

        return new SuccessResponse<>("Thành công",new PagingResponse<>(pagingResult.getTotalPages(),(int)pagingResult.getTotalElements(),postListVm));

    }

    private List<Tag> createListTag(List<CreateTagDTO> dto){
        List<Tag> listTag = new ArrayList<>();

        dto.forEach(item ->{
            var foundTag = tagRepository.findBySlug(item.getSlug()).orElse(null);
            if(foundTag == null){
                foundTag = tagService.createTag(item).getData();
            }
            listTag.add(foundTag);
        });
        return listTag;
    }


}
