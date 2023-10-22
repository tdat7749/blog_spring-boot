package com.example.blog_springboot.modules.likepost.service;

import com.example.blog_springboot.commons.SuccessResponse;
import com.example.blog_springboot.modules.likepost.constant.LikePostConstants;
import com.example.blog_springboot.modules.likepost.exception.LikePostException;
import com.example.blog_springboot.modules.likepost.exception.LikedPostException;
import com.example.blog_springboot.modules.likepost.exception.NotYetLikedPostException;
import com.example.blog_springboot.modules.likepost.model.LikePost;
import com.example.blog_springboot.modules.likepost.repository.LikePostRepository;
import com.example.blog_springboot.modules.post.constant.PostConstants;
import com.example.blog_springboot.modules.post.exception.PostNotFoundException;
import com.example.blog_springboot.modules.post.model.Post;
import com.example.blog_springboot.modules.post.repository.PostRepository;
import com.example.blog_springboot.modules.user.model.User;
import com.example.blog_springboot.modules.user.viewmodel.UserDetailVm;
import com.example.blog_springboot.utils.Utilities;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service
public class LikePostServiceImpl implements LikePostService{

    private final LikePostRepository likePostRepository;
    private final PostRepository postRepository;

    public LikePostServiceImpl(LikePostRepository likePostRepository,PostRepository postRepository){
        this.likePostRepository = likePostRepository;
        this.postRepository = postRepository;
    }

    @Override
    public SuccessResponse<Boolean> likePost(int postId, User userPrincipal) {

        var foundPost = postRepository.findById(postId).orElse(null);
        if(foundPost == null){
            throw new PostNotFoundException(PostConstants.POST_NOT_FOUND);
        }

        var isLiked = likePostRepository.existsByUserAndPost(userPrincipal,foundPost);
        if(isLiked){
            throw new LikedPostException(LikePostConstants.LIKED_POST);
        }

        LikePost likePost = new LikePost();
        likePost.setUser(userPrincipal);
        likePost.setPost(foundPost);
        likePost.setCreatedAt(new Date());
        likePost.setUpdatedAt(new Date());

        var saveLike = likePostRepository.save(likePost);
        if(saveLike == null){
            throw new LikePostException(LikePostConstants.LIKE_POST_FAILED);
        }

        return new SuccessResponse<>(LikePostConstants.LIKE_POST_SUCCESS,true);
    }

    @Override
    public SuccessResponse<Boolean> unLikePost(int postId, User userPrincipal) {
        var foundPost = postRepository.findById(postId).orElse(null);
        if(foundPost == null){
            throw new PostNotFoundException(PostConstants.POST_NOT_FOUND);
        }

        var isLiked = likePostRepository.findByUserAndPost(userPrincipal,foundPost).orElse(null);
        if(isLiked == null){
            throw new NotYetLikedPostException(LikePostConstants.NOT_YET_LIKED_POST);
        }

        likePostRepository.delete(isLiked);

        return new SuccessResponse<>(LikePostConstants.UNLIKE_POST_SUCCESS,true);

    }

    @Override
    public SuccessResponse<Boolean> checkUserLikedPost(String postSlug, User userPrincipal) {
        var foundPost = postRepository.findBySlug(postSlug).orElse(null);
        if(foundPost == null){
            throw new PostNotFoundException(PostConstants.POST_NOT_FOUND);
        }

        var isLiked = likePostRepository.existsByUserAndPost(userPrincipal,foundPost);
        if(isLiked){
            return new SuccessResponse<>("Thành công",true);
        }

        return new SuccessResponse<>("Thành công",false);
    }

    @Override
    public SuccessResponse<List<UserDetailVm>> getListUserLikedPost(int postId) {
        var foundPost = postRepository.findById(postId).orElse(null);
        if(foundPost == null){
            throw new PostNotFoundException(PostConstants.POST_NOT_FOUND);
        }

        var list = likePostRepository.findByPost(foundPost);
        List<UserDetailVm> result = list.stream().map(item -> {
            var user = item.getUser();
            return Utilities.getUserDetailVm(user);
        }).toList();

        return new SuccessResponse<>("Thành công",result);
    }

    @Override
    public long countLikePost(Post post) {
        return likePostRepository.getTotalLike(post);
    }
}
