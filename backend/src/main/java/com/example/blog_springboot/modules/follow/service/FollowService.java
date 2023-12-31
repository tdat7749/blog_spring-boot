package com.example.blog_springboot.modules.follow.service;

import com.example.blog_springboot.commons.SuccessResponse;
import com.example.blog_springboot.modules.follow.dto.FollowDTO;
import com.example.blog_springboot.modules.user.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Service;

@Service
public interface FollowService {

    public SuccessResponse<Boolean> follow(int followingId, User userPrincipal) throws JsonProcessingException;
    public SuccessResponse<Boolean> unFollow(int followingId,User userPrincipal);

    public SuccessResponse<Boolean> checkFollowed(String userName,User userPrincipal);

}
