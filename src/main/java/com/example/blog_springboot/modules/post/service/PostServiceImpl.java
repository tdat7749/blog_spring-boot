package com.example.blog_springboot.modules.post.service;

import com.example.blog_springboot.commons.Constants;
import com.example.blog_springboot.commons.PagingResponse;
import com.example.blog_springboot.commons.SuccessResponse;
import com.example.blog_springboot.modules.post.constant.PostConstants;
import com.example.blog_springboot.modules.post.dto.CreatePostDTO;
import com.example.blog_springboot.modules.post.dto.UpdatePostDTO;
import com.example.blog_springboot.modules.post.exception.*;
import com.example.blog_springboot.modules.post.model.Post;
import com.example.blog_springboot.modules.post.repository.PostRepository;
import com.example.blog_springboot.modules.post.viewmodel.PostListVm;
import com.example.blog_springboot.modules.post.viewmodel.PostVm;
import com.example.blog_springboot.modules.series.constant.SeriesConstants;
import com.example.blog_springboot.modules.series.exception.SeriesNotFoundException;
import com.example.blog_springboot.modules.series.repository.SeriesRepository;
import com.example.blog_springboot.modules.tag.constant.TagConstants;
import com.example.blog_springboot.modules.tag.exception.TagNotFoundException;
import com.example.blog_springboot.modules.tag.model.Tag;
import com.example.blog_springboot.modules.tag.repository.TagRepository;
import com.example.blog_springboot.modules.tag.serivce.TagService;
import com.example.blog_springboot.modules.tag.viewmodel.TagVm;
import com.example.blog_springboot.modules.user.model.User;
import com.example.blog_springboot.modules.user.viewmodel.UserVm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final SeriesRepository seriesRepository;
    private final TagRepository tagRepository;
    private final TagService tagService;

    public PostServiceImpl(PostRepository postRepository,SeriesRepository seriesRepository,TagRepository tagRepository,TagService tagService){
        this.postRepository = postRepository;
        this.seriesRepository = seriesRepository;
        this.tagRepository = tagRepository;
        this.tagService = tagService;
    }

    @Override
    public SuccessResponse<PagingResponse<List<PostListVm>>> getAllPost(String sortBy, int pageIndex) {
        Pageable paging = PageRequest.of(pageIndex, Constants.PAGE_SIZE, Sort.by(sortBy));

        Page<Post> pagingResult = postRepository.findAllByPublished(true,paging);

        List<PostListVm> listPostVm = pagingResult.stream().map(this::getPostListVm).toList();

        return new SuccessResponse<>("Thành công",new PagingResponse<>(pagingResult.getTotalPages(),(int)pagingResult.getTotalElements(),listPostVm));
    }

    @Override
    public SuccessResponse<PostListVm> createPost(CreatePostDTO dto, User userPrincipal) {
        var foundPostBySlug = postRepository.findBySlug(dto.getSlug()).orElse(null);
        if(foundPostBySlug != null){
            throw new PostSlugDuplicateException(PostConstants.POST_SLUG_DUPLICATE);
        }

        List<Tag> listTag = new ArrayList<>();

        dto.getListTags().stream().map(item ->{
            var foundTag = tagRepository.findBySlug(item.getSlug()).orElse(null);
//            if(foundTag == null){
//                foundTag = tagRepository.save(item)
//            }
            return listTag.add(foundTag);
        });

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

        var newPostListVm = getPostListVm(savePost);
        return new SuccessResponse<>(PostConstants.CREATE_POST_SUCCESS,newPostListVm);
    }

    @Override
    public SuccessResponse<PostListVm> updatePost(UpdatePostDTO dto, User userPrincipal) {
        var foundPostByUserAndId = postRepository.findByUserAndId(userPrincipal,dto.getId()).orElse(null);
        if(foundPostByUserAndId == null){
            throw new NotAuthorPostException(PostConstants.NOT_AUTHOR_POST);
        }

        var foundPostBySlug = postRepository.findBySlug(dto.getSlug()).orElse(null);
        if(foundPostBySlug != null && foundPostBySlug != foundPostByUserAndId){
            throw new PostSlugDuplicateException(PostConstants.POST_SLUG_DUPLICATE);
        }

        foundPostByUserAndId.setTitle(dto.getTitle());
        foundPostByUserAndId.setContent(dto.getContent());
        foundPostByUserAndId.setSlug(dto.getSlug());
        foundPostByUserAndId.setSummary(dto.getSummary());
        foundPostByUserAndId.setUpdatedAt(new Date());

        if(dto.getThumbnail() != null){
            foundPostByUserAndId.setThumbnail(dto.getThumbnail());
        }



    }

    @Override
    public SuccessResponse<Boolean> deletePost(int id, User userPrincipal) {
        var foundPostByUserAndId = postRepository.findByUserAndId(userPrincipal,id).orElse(null);
        if(foundPostByUserAndId == null){
            throw new NotAuthorPostException(PostConstants.NOT_AUTHOR_POST);
        }

        postRepository.delete(foundPostByUserAndId);

        return new SuccessResponse<>(PostConstants.DELETE_POST_SUCCESS,true);

    }

    @Override
    public SuccessResponse<PostVm> getPostBySlug(String slug) {
        var foundPost = postRepository.findBySlugAndPublished(slug,true).orElse(null);
        if(foundPost == null){
            throw new PostNotFoundException(PostConstants.POST_NOT_FOUND);
        }

        var postVm = getPostVm(foundPost);

        return new SuccessResponse<>("Thành công",postVm);

    }

    @Override
    public SuccessResponse<Boolean> updateStatus(int id,User userPrincipal,boolean status) {
        var foundPostByUserAndId = postRepository.findByUserAndId(userPrincipal,id).orElse(null);
        if(foundPostByUserAndId == null){
            throw new NotAuthorPostException(PostConstants.NOT_AUTHOR_POST);
        }

        foundPostByUserAndId.setPublished(status);
        var savePost = postRepository.save(foundPostByUserAndId);
        if(savePost == null){
            throw new UpdatePostException(PostConstants.CHANGE_POST_STATUS_FAILED);
        }

        return new SuccessResponse<>(PostConstants.CHANGE_POST_STATUS_SUCCESS,true);
    }

    @Override
    public SuccessResponse<PagingResponse<List<PostListVm>>> getAllPostByTag(String tagSlug, String sortBy, int pageIndex) {
        Pageable paging = PageRequest.of(pageIndex,Constants.PAGE_SIZE,Sort.by(sortBy));

        Page<Post> pagingResult = postRepository.getPostByTagSlug(tagSlug,paging);

        var postListVm = pagingResult.stream().map(this::getPostListVm).toList();

        return new SuccessResponse<>("Thành công",new PagingResponse<>(pagingResult.getTotalPages(),(int)pagingResult.getTotalElements(),postListVm));

    }

    private PostListVm getPostListVm(Post post){
        PostListVm postListVm = new PostListVm();
        UserVm userVm = new UserVm();

        postListVm.setId(post.getId());
        postListVm.setTitle(post.getTitle());
        postListVm.setSlug(post.getSlug());
        postListVm.setSummary(post.getSummary());
        postListVm.setThumbnail(post.getThumbnail());
        postListVm.setCreatedAt(post.getCreatedAt().toString());
        postListVm.setUpdatedAt(post.getUpdatedAt().toString());

        // set userVm
        userVm.setUserName(post.getUser().getUsername());
        userVm.setLastName(post.getUser().getLastName());
        userVm.setFirstName(post.getUser().getFirstName());
        userVm.setUserName(post.getUser().getUsername());
        userVm.setId(post.getUser().getId());

        // set tagVm
        List<TagVm> listTagVm = post.getTags().stream().map(tag -> {
            TagVm tagVm = new TagVm();
            tagVm.setId(tag.getId());
            tagVm.setTitle(tag.getTitle());
            tagVm.setSlug(tag.getSlug());

            return tagVm;
        }).toList();

        postListVm.setTags(listTagVm);
        postListVm.setAuthor(userVm);

        return postListVm;
    }

    private PostVm getPostVm(Post post){
        PostVm postVm = new PostVm();

        UserVm userVm = new UserVm();

        postVm.setId(post.getId());
        postVm.setTitle(post.getTitle());
        postVm.setSlug(post.getSlug());
        postVm.setSummary(post.getSummary());
        postVm.setThumbnail(post.getThumbnail());
        postVm.setCreatedAt(post.getCreatedAt().toString());
        postVm.setUpdatedAt(post.getUpdatedAt().toString());
        postVm.setContent(post.getContent());

        // set userVm
        userVm.setUserName(post.getUser().getUsername());
        userVm.setLastName(post.getUser().getLastName());
        userVm.setFirstName(post.getUser().getFirstName());
        userVm.setUserName(post.getUser().getUsername());
        userVm.setId(post.getUser().getId());

        // set tagVm
        List<TagVm> listTagVm = post.getTags().stream().map(tag -> {
            TagVm tagVm = new TagVm();
            tagVm.setId(tag.getId());
            tagVm.setTitle(tag.getTitle());
            tagVm.setSlug(tag.getSlug());

            return tagVm;
        }).toList();

        postVm.setTags(listTagVm);
        postVm.setAuthor(userVm);

        return postVm;
    }
}
