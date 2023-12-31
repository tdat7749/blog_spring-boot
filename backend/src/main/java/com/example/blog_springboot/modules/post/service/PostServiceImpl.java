package com.example.blog_springboot.modules.post.service;

import com.example.blog_springboot.commons.Constants;
import com.example.blog_springboot.commons.PagingResponse;
import com.example.blog_springboot.commons.SuccessResponse;
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
import com.example.blog_springboot.modules.user.enums.Role;
import com.example.blog_springboot.modules.user.model.User;
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
            FollowRepository followRepository) {
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
    public SuccessResponse<PagingResponse<List<PostListVm>>> getAllPostPublished(String keyword, String sortBy,
            int pageIndex) {
        Pageable paging = null;
        if(sortBy.equals("createdAt")){
            paging =  PageRequest.of(pageIndex, Constants.PAGE_SIZE, Sort.by(Sort.Direction.DESC, sortBy));
        }else if (sortBy.equals("newest")) {
            paging =  PageRequest.of(pageIndex, Constants.PAGE_SIZE, Sort.by(Sort.Direction.DESC, "createdAt"));
        }
        else if (sortBy.equals("latest")){
            paging =  PageRequest.of(pageIndex, Constants.PAGE_SIZE, Sort.by(Sort.Direction.ASC, "createdAt"));
        }
        else if (sortBy.equals("totalView")){
            paging =  PageRequest.of(pageIndex, Constants.PAGE_SIZE, Sort.by(Sort.Direction.DESC, sortBy));
        }else{
            paging =  PageRequest.of(pageIndex, Constants.PAGE_SIZE, Sort.by(Sort.Direction.DESC, sortBy));
        }

        Page<Post> pagingResult = postRepository.findAllByPublished(true, keyword, paging);

        List<PostListVm> listPostVm = pagingResult.stream().map(Utilities::getPostListVm).toList();

        return new SuccessResponse<>("Thành công",
                new PagingResponse<>(pagingResult.getTotalPages(), (int) pagingResult.getTotalElements(), listPostVm));
    }

    @Override
    public SuccessResponse<PostVm> getPostById(int id) {
        var foundPost = postRepository.findById(id).orElse(null);
        if (foundPost == null) {
            throw new PostNotFoundException(PostConstants.POST_NOT_FOUND);
        }

        var postVm = Utilities.getPostVm(foundPost);

        return new SuccessResponse<>("Thành công", postVm);
    }

    @Override
    @Transactional
    public SuccessResponse<PostListVm> createPost(CreatePostDTO dto, User userPrincipal)
            throws JsonProcessingException {
        if (dto.getListTags().size() > 3) {
            throw new MaxTagException(PostConstants.MAX_TAG);
        }

        var foundPostBySlug = postRepository.findBySlug(dto.getSlug()).orElse(null);
        if (foundPostBySlug != null) {
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

        if (dto.getSeriesId() != null) {
            var foundSeries = seriesRepository.findById(dto.getSeriesId()).orElse(null);
            if (foundSeries == null) {
                throw new SeriesNotFoundException(SeriesConstants.SERIES_NOT_FOUND);
            }
            newPost.setSeries(foundSeries);
        }

        var savePost = postRepository.save(newPost);
        if (savePost == null) {
            throw new CreatePostException(PostConstants.CREATE_POST_FAILED);
        }

        // sau khi khởi tạo post thành công thì tạo ra 1 thông báo
        CreateNotificationDTO notification = new CreateNotificationDTO();
        notification.setLink("");
        notification.setMessage("Người dùng @" + userPrincipal.getUsername()
                + " mà bạn đang theo dõi chuẩn bị đăng bài viết có tựa đề là: " + savePost.getTitle());

        var newNotification = notificationService.createNotification(notification);

        // sau đó nối thông báo với user
        List<User> users = followRepository.getListFollowersByUser(userPrincipal);

        userNotificationService.createUserNotification(users, newNotification);

        // Tạo ra 1 notification view model và gửi tới client
        NotificationVm notificationVm = new NotificationVm();
        notificationVm.setId(newNotification.getId());
        notificationVm.setLink(newNotification.getLink());
        notificationVm.setMessage(newNotification.getMessage());
        notificationVm.setRead(false);
        notificationVm.setCreatedAt(newNotification.getCreatedAt().toString());

        webSocketService.sendNotificationToClient(users, notificationVm);

        var newPostListVm = Utilities.getPostListVm(savePost);
        return new SuccessResponse<>(PostConstants.CREATE_POST_SUCCESS, newPostListVm);
    }

    @Override
    @Transactional
    public SuccessResponse<PostVm> updatePost(UpdatePostDTO dto, int id, User userPrincipal) {
        if (!(userPrincipal.getRole() == Role.ADMIN)) {
            var isAuthor = postRepository.existsByUserAndId(userPrincipal, id);
            if (!isAuthor) {
                throw new NotAuthorPostException(PostConstants.NOT_AUTHOR_POST);
            }
        }

        if (dto.getListTags().size() > 3) {
            throw new MaxTagException(PostConstants.MAX_TAG);
        }

        var foundPost = postRepository.findById(id).orElse(null);
        if (foundPost == null) {
            throw new PostNotFoundException(PostConstants.POST_NOT_FOUND);
        }

        var foundPostBySlug = postRepository.findBySlug(dto.getSlug()).orElse(null);
        if (foundPostBySlug != null && foundPostBySlug != foundPost) {
            throw new PostSlugDuplicateException(PostConstants.POST_SLUG_DUPLICATE);
        }

        foundPost.setTitle(dto.getTitle());
        foundPost.setContent(dto.getContent());
        foundPost.setSlug(dto.getSlug());
        foundPost.setSummary(dto.getSummary());
        foundPost.setUpdatedAt(new Date());

        if (dto.getThumbnail() != null) {
            foundPost.setThumbnail(dto.getThumbnail());
        }

        if (dto.getSeriesId() != null) {
            var foundSeries = seriesRepository.findById(dto.getSeriesId()).orElse(null);
            if (foundSeries == null) {
                throw new SeriesNotFoundException(SeriesConstants.SERIES_NOT_FOUND);
            }
            foundPost.setSeries(foundSeries);
        }

        // new tag update
        List<Tag> addTags = createListTag(dto.getListTags());

        foundPost.removeAllTag(foundPost.getTags());
        foundPost.addAllTag(addTags);

        var savePost = postRepository.save(foundPost);
        if (savePost == null) {
            throw new UpdatePostException(PostConstants.UPDATE_POST_FAILED);
        }

        var postListVm = Utilities.getPostVm(savePost);

        return new SuccessResponse<>(PostConstants.UPDATE_POST_SUCCESS, postListVm);
    }

    @Override
    public SuccessResponse<Boolean> deletePost(int id, User userPrincipal) {
        if (!(userPrincipal.getRole() == Role.ADMIN)) {
            var isAuthor = postRepository.existsByUserAndId(userPrincipal, id);
            if (!isAuthor) {
                throw new NotAuthorPostException(PostConstants.NOT_AUTHOR_POST);
            }
        }

        var foundPost = postRepository.findById(id).orElse(null);
        if (foundPost == null) {
            throw new NotAuthorPostException(PostConstants.NOT_AUTHOR_POST);
        }

        postRepository.delete(foundPost);

        return new SuccessResponse<>(PostConstants.DELETE_POST_SUCCESS, true);

    }

    @Override
    public SuccessResponse<PostVm> getPostBySlug(String slug) {
        var foundPost = postRepository.getPostBySlug(slug).orElse(null);
        if (foundPost == null) {
            throw new PostNotFoundException(PostConstants.POST_NOT_FOUND);
        }

        var postVm = Utilities.getPostVm(foundPost);

        return new SuccessResponse<>("Thành công", postVm);

    }

    @Override
    public SuccessResponse<PostVm> getPostBySlug(String slug, User user) {
        var foundPost = postRepository.getPostBySlug(slug, user).orElse(null);
        if (foundPost == null) {
            throw new PostNotFoundException(PostConstants.POST_NOT_FOUND);
        }

        var postVm = Utilities.getPostVm(foundPost);

        return new SuccessResponse<>("Thành công", postVm);
    }

    @Override
    public SuccessResponse<PagingResponse<List<PostListVm>>> getAllPostAuthor(String username, String keyword,
            String sortBy, int pageIndex) {
        Pageable paging = PageRequest.of(pageIndex, Constants.PAGE_SIZE, Sort.by(Sort.Direction.DESC, sortBy));

        Page<Post> pagingResult = postRepository.getAllPostByUsername(keyword, username, paging);

        List<PostListVm> listPostVm = pagingResult.stream().map(Utilities::getPostListVm).toList();

        return new SuccessResponse<>("Thành công",
                new PagingResponse<>(pagingResult.getTotalPages(), (int) pagingResult.getTotalElements(), listPostVm));
    }

    @Override
    public SuccessResponse<PagingResponse<List<PostListVm>>> getAllPostNotPublished(String sortBy, String keyword,
            int pageIndex) {
        Pageable paging = PageRequest.of(pageIndex, Constants.PAGE_SIZE, Sort.by(Sort.Direction.DESC, sortBy));

        Page<Post> pagingResult = postRepository.getAllPostNotPublished(keyword, paging);

        List<PostListVm> listPostVm = pagingResult.stream().map(Utilities::getPostListVm).toList();

        return new SuccessResponse<>("Thành công",
                new PagingResponse<>(pagingResult.getTotalPages(), (int) pagingResult.getTotalElements(), listPostVm));
    }

    @Override
    public SuccessResponse<PagingResponse<List<PostListVm>>> getAllByCurrentUser(User user, String keyword,
            String sortBy, int pageIndex) {
        Pageable paging = PageRequest.of(pageIndex, Constants.PAGE_SIZE, Sort.by(Sort.Direction.DESC, sortBy));

        Page<Post> pagingResult = postRepository.getAllPostByCurrentUser(keyword, user, paging);

        List<PostListVm> listPostVm = pagingResult.stream().map(Utilities::getPostListVm).toList();

        return new SuccessResponse<>("Thành công",
                new PagingResponse<>(pagingResult.getTotalPages(), (int) pagingResult.getTotalElements(), listPostVm));
    }

    @Override
    public SuccessResponse<List<PostListVm>> getAllPostNotBeLongSeries(User user) {
        var listPost = postRepository.getAllPostNotBeLongSeries(user);

        List<PostListVm> listPostVm = listPost.stream().map(Utilities::getPostListVm).toList();

        return new SuccessResponse<>("Thành công", listPostVm);
    }

    @Override
    public SuccessResponse<List<PostListVm>> getLatestPost() {
        Pageable paging = PageRequest.of(0, 8);

        Page<Post> pagingResult = postRepository.getLatestPost(paging);

        var listPostVm = pagingResult.stream().map(Utilities::getPostListVm).toList();

        return new SuccessResponse<>("Thành công", listPostVm);
    }

    @Override
    public SuccessResponse<List<PostListVm>> getPostMostView() {
        Pageable paging = PageRequest.of(0, 5);

        Page<Post> pagingResult = postRepository.getPostMostView(paging);

        var listPostVm = pagingResult.stream().map(Utilities::getPostListVm).toList();

        return new SuccessResponse<>("Thành công", listPostVm);
    }

    @Override
    public SuccessResponse<Boolean> plusView(String postSlug) {
        var foundPost = postRepository.findBySlug(postSlug).orElse(null);
        if (foundPost == null) {
            throw new PostNotFoundException(PostConstants.POST_NOT_FOUND);
        }

        foundPost.setTotalView(foundPost.getTotalView() + 1);
        postRepository.save(foundPost);
        return new SuccessResponse<>("Thành công", true);
    }

    @Transactional
    @Override
    public SuccessResponse<Boolean> updateStatus(int id, User userPrincipal, UpdatePostStatusDTO dto)
            throws JsonProcessingException {
        if (userPrincipal.getRole() == Role.ADMIN) {
            var foundPost = postRepository.findById(id).orElse(null);
            if (foundPost == null) {
                throw new NotAuthorPostException(PostConstants.NOT_AUTHOR_POST);
            }

            foundPost.setPublished(dto.isStatus());
            var savePost = postRepository.save(foundPost);

            if (savePost == null) {
                throw new UpdatePostException(PostConstants.CHANGE_POST_STATUS_FAILED);
            }

            var postAuthor = savePost.getUser();

            CreateNotificationDTO notification = new CreateNotificationDTO();
            notification.setLink("/bai-viet/" + savePost.getSlug());
            notification.setMessage("Người dùng @" + postAuthor.getUsername()
                    + " mà bạn đang theo dõi vừa đăng bài viết có tựa đề là: " + savePost.getTitle());

            var newNotification = notificationService.createNotification(notification);

            // sau đó nối thông báo với user
            List<User> users = followRepository.getListFollowersByUser(postAuthor);

            userNotificationService.createUserNotification(users, newNotification);

            // Tạo ra 1 notification view model và gửi tới client
            NotificationVm notificationVm = new NotificationVm();
            notificationVm.setId(newNotification.getId());
            notificationVm.setLink(newNotification.getLink());
            notificationVm.setMessage(newNotification.getMessage());
            notificationVm.setRead(false);
            notificationVm.setCreatedAt(newNotification.getCreatedAt().toString());

            webSocketService.sendNotificationToClient(users, notificationVm);

            return new SuccessResponse<>(PostConstants.CHANGE_POST_STATUS_SUCCESS, true);
        }
        throw new NotAuthorPostException(PostConstants.NOT_AUTHOR_POST);
    }

    @Override
    public SuccessResponse<Boolean> addPostToSeries(int postId, int seriesId, User userPrincipal)
            throws JsonProcessingException {
        if (!(userPrincipal.getRole() == Role.ADMIN)) {
            var isPostAuthor = postRepository.existsByUserAndId(userPrincipal, postId);
            if (!isPostAuthor) {
                throw new NotAuthorPostException(PostConstants.NOT_AUTHOR_POST);
            }

            var isSeriesAuthor = seriesRepository.existsByUserAndId(userPrincipal, seriesId);
            if (!isSeriesAuthor) {
                throw new NotAuthorSeriesException(SeriesConstants.NOT_AUTHOR_SERIES);
            }
        }

        var foundPost = postRepository.findById(postId).orElse(null);
        if (foundPost == null) {
            throw new PostNotFoundException(PostConstants.POST_NOT_FOUND);
        }

        var foundSeries = seriesRepository.findById(seriesId).orElse(null);
        if (foundSeries == null) {
            throw new SeriesNotFoundException(SeriesConstants.SERIES_NOT_FOUND);
        }

        if (foundPost.getSeries() != null) {
            throw new RemovePostFromSeriesException(PostConstants.POST_HAS_BELONG_SERIES);
        }

        foundPost.setSeries(foundSeries);

        var savePost = postRepository.save(foundPost);

        if (savePost == null) {
            throw new UpdatePostException(PostConstants.ADD_POST_SERIES_FAILED);
        }

        // sau khi khởi tạo post thành công thì tạo ra 1 thông báo
        CreateNotificationDTO notification = new CreateNotificationDTO();
        notification.setLink("/series/" + foundSeries.getSlug());
        notification.setMessage("Người dùng @" + foundSeries.getUser().getUsername()
                + " mà bạn đang theo dõi vừa cập nhật series " + foundSeries.getTitle() + " của họ");

        var newNotification = notificationService.createNotification(notification);

        // sau đó nối thông báo với user
        List<User> users = followRepository.getListFollowersByUser(userPrincipal);

        userNotificationService.createUserNotification(users, newNotification);

        // Tạo ra 1 notification view model và gửi tới client
        NotificationVm notificationVm = new NotificationVm();
        notificationVm.setId(newNotification.getId());
        notificationVm.setLink(newNotification.getLink());
        notificationVm.setMessage(newNotification.getMessage());
        notificationVm.setRead(false);
        notificationVm.setCreatedAt(newNotification.getCreatedAt().toString());

        webSocketService.sendNotificationToClient(users, notificationVm);

        return new SuccessResponse<>(PostConstants.ADD_POST_SERIES_SUCCESS, true);

    }

    @Override
    public SuccessResponse<Boolean> removePostFromSeries(int postId, int seriesId, User userPrincipal) {
        if (!(userPrincipal.getRole() == Role.ADMIN)) {
            var isPostAuthor = postRepository.existsByUserAndId(userPrincipal, postId);
            if (!isPostAuthor) {
                throw new NotAuthorPostException(PostConstants.NOT_AUTHOR_POST);
            }

            var isSeriesAuthor = seriesRepository.existsByUserAndId(userPrincipal, seriesId);
            if (!isSeriesAuthor) {
                throw new NotAuthorSeriesException(SeriesConstants.NOT_AUTHOR_SERIES);
            }
        }

        var foundPost = postRepository.findById(postId).orElse(null);
        if (foundPost == null) {
            throw new PostNotFoundException(PostConstants.POST_NOT_FOUND);
        }

        var foundSeries = seriesRepository.findById(seriesId).orElse(null);
        if (foundSeries == null) {
            throw new SeriesNotFoundException(SeriesConstants.SERIES_NOT_FOUND);
        }

        if (foundPost.getSeries() == null) {
            throw new RemovePostFromSeriesException(PostConstants.POST_NOT_BELONG_SERIES);
        }

        foundPost.setSeries(null);

        var savePost = postRepository.save(foundPost);

        if (savePost == null) {
            throw new RemovePostFromSeriesException(PostConstants.REMOVE_POST_FROM_FAILED);
        }

        return new SuccessResponse<>(PostConstants.REMOVE_POST_FROM_SUCCESS, true);
    }

    @Override
    public SuccessResponse<PagingResponse<List<PostListVm>>> getAllPostByTag(String tagSlug, String keyword,
            String sortBy, int pageIndex) {
        Pageable paging = PageRequest.of(pageIndex, Constants.PAGE_SIZE, Sort.by(Sort.Direction.DESC, sortBy));

        Page<Post> pagingResult = postRepository.getPostByTagSlug(keyword, tagSlug, paging);

        var postListVm = pagingResult.stream().map(Utilities::getPostListVm).toList();

        return new SuccessResponse<>("Thành công",
                new PagingResponse<>(pagingResult.getTotalPages(), (int) pagingResult.getTotalElements(), postListVm));

    }

    private List<Tag> createListTag(List<CreateTagDTO> dto) {
        List<Tag> listTag = new ArrayList<>();

        dto.forEach(item -> {
            var foundTag = tagRepository.findBySlug(item.getSlug()).orElse(null);
            if (foundTag == null) {
                foundTag = tagService.createTag(item).getData();
            }
            listTag.add(foundTag);
        });
        return listTag;
    }

    @Override
    public SuccessResponse<PagingResponse<List<PostListVm>>> getAllPosts(String keyword, String sortBy, int pageIndex) {
        Pageable paging = PageRequest.of(pageIndex, Constants.PAGE_SIZE, Sort.by(Sort.Direction.DESC, sortBy));

        Page<Post> pagingResult = postRepository.getAllPosts(keyword, paging);

        List<PostListVm> listPostVm = pagingResult.stream().map(Utilities::getPostListVm).toList();

        return new SuccessResponse<>("Thành công",
                new PagingResponse<>(pagingResult.getTotalPages(), (int) pagingResult.getTotalElements(), listPostVm));
    }

}
