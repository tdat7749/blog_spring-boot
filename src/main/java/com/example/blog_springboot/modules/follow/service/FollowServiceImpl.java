package com.example.blog_springboot.modules.follow.service;

import com.example.blog_springboot.commons.SuccessResponse;
import com.example.blog_springboot.modules.authenticate.constant.AuthConstants;
import com.example.blog_springboot.modules.authenticate.exception.UserNotFoundException;
import com.example.blog_springboot.modules.follow.constants.FollowConstants;
import com.example.blog_springboot.modules.follow.exception.FollowException;
import com.example.blog_springboot.modules.follow.exception.FollowedException;
import com.example.blog_springboot.modules.follow.exception.NotYetFollowed;
import com.example.blog_springboot.modules.follow.model.Follow;
import com.example.blog_springboot.modules.follow.repository.FollowRepository;
import com.example.blog_springboot.modules.user.model.User;
import com.example.blog_springboot.modules.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class FollowServiceImpl implements FollowService {

    private final FollowRepository followRepository;
    private final UserRepository userRepository;

    public FollowServiceImpl(FollowRepository followRepository,UserRepository userRepository){
        this.followRepository = followRepository;
        this.userRepository = userRepository;
    }

    @Override
    public SuccessResponse<Boolean> follow(int followingId, User userPrincipal) {
        var userFound = userRepository.findById(followingId).orElse(null);
        if(userFound == null){
            throw new UserNotFoundException(AuthConstants.USER_NOT_FOUND);
        }

        var isFollowed = followRepository.findByFollowersAndFollowing(userPrincipal,userFound).orElse(null);
        if(isFollowed != null){
            throw new FollowedException(FollowConstants.FOLLOWED);
        }

        Follow follow = new Follow();
        follow.setFollowers(userPrincipal);
        follow.setFollowing(userFound);
        follow.setCreatedAt(new Date());
        follow.setUpdatedAt(new Date());

        var save = followRepository.save(follow);
        if(save == null){
            throw new FollowException(FollowConstants.FOLLOW_FAILED);
        }

        return new SuccessResponse<>(FollowConstants.FOLLOW_SUCCESS,true);
    }

    @Override
    public SuccessResponse<Boolean> unFollow(int followingId, User userPrincipal) {
        var userFound = userRepository.findById(followingId).orElse(null);
        if(userFound == null){
            throw new UserNotFoundException(AuthConstants.USER_NOT_FOUND);
        }

        var isFollowed = followRepository.findByFollowersAndFollowing(userPrincipal,userFound).orElse(null);
        if(isFollowed == null){
            throw new NotYetFollowed(FollowConstants.NOT_YET_FOLLOWED);
        }

        followRepository.delete(isFollowed);
        return new SuccessResponse<>(FollowConstants.UNFOLLOW_SUCCESS,true);
    }
}
