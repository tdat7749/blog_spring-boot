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
import com.example.blog_springboot.modules.notification.dto.CreateNotificationDTO;
import com.example.blog_springboot.modules.notification.model.Notification;
import com.example.blog_springboot.modules.notification.service.NotificationService;
import com.example.blog_springboot.modules.notification.service.UserNotificationService;
import com.example.blog_springboot.modules.notification.viewmodel.NotificationVm;
import com.example.blog_springboot.modules.user.model.User;
import com.example.blog_springboot.modules.user.repository.UserRepository;
import com.example.blog_springboot.modules.websocket.service.WebSocketService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class FollowServiceImpl implements FollowService {

    private final FollowRepository followRepository;
    private final UserRepository userRepository;

    private final NotificationService notificationService;

    private final UserNotificationService userNotificationService;

    private final WebSocketService webSocketService;

    public FollowServiceImpl(
            FollowRepository followRepository,
            UserRepository userRepository,
            NotificationService notificationService,
            UserNotificationService userNotificationService,
            WebSocketService webSocketService
    ){
        this.followRepository = followRepository;
        this.userRepository = userRepository;
        this.notificationService = notificationService;
        this.userNotificationService = userNotificationService;
        this.webSocketService = webSocketService;
    }

    @Override
    @Transactional
    public SuccessResponse<Boolean> follow(int followingId, User userPrincipal) throws JsonProcessingException {
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


        // sau khi khởi tạo follow thành công thì tạo ra 1 thông báo
        CreateNotificationDTO notification = new CreateNotificationDTO();
        notification.setLink("");
        notification.setMessage("");

        var newNotification = notificationService.createNotification(notification);

        // sau đó nối thông báo với user
        List<User> users = new ArrayList<>();
        users.add(userFound);
        userNotificationService.createUserNotification(users,newNotification);

        // Tạo ra 1 notification view model và gửi tới client
        NotificationVm notificationVm = new NotificationVm();
        notificationVm.setId(newNotification.getId());
        notificationVm.setLink(newNotification.getLink());
        notificationVm.setMessage(newNotification.getMessage());
        notificationVm.setRead(false);

        webSocketService.sendNotificationToClient(userFound.getUsername(),notificationVm);

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
