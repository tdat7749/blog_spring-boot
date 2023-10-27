package com.example.blog_springboot.modules.likepost.service;

import com.example.blog_springboot.commons.SuccessResponse;
import com.example.blog_springboot.modules.post.model.Post;
import com.example.blog_springboot.modules.user.model.User;
import com.example.blog_springboot.modules.user.viewmodel.UserDetailVm;
import com.fasterxml.jackson.core.JsonProcessingException;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface LikePostService {
    public SuccessResponse<Boolean> likePost(int postId, User userPrincipal) throws JsonProcessingException;

    public SuccessResponse<Boolean> unLikePost(int postId, User userPrincipal);

    public SuccessResponse<Boolean> checkUserLikedPost(String postSlug, User userPrincipal);

    public SuccessResponse<List<UserDetailVm>> getListUserLikedPost(int postId);

    public long countLikePost(Post post);
}
