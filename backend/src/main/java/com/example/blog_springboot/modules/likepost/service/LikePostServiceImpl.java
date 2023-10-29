package com.example.blog_springboot.modules.likepost.service;

import com.example.blog_springboot.commons.SuccessResponse;
import com.example.blog_springboot.modules.likepost.constant.LikePostConstants;
import com.example.blog_springboot.modules.likepost.exception.LikePostException;
import com.example.blog_springboot.modules.likepost.exception.LikedPostException;
import com.example.blog_springboot.modules.likepost.exception.NotYetLikedPostException;
import com.example.blog_springboot.modules.likepost.model.LikePost;
import com.example.blog_springboot.modules.likepost.repository.LikePostRepository;
import com.example.blog_springboot.modules.notification.dto.CreateNotificationDTO;
import com.example.blog_springboot.modules.notification.model.UserNotification;
import com.example.blog_springboot.modules.notification.service.NotificationService;
import com.example.blog_springboot.modules.notification.service.UserNotificationService;
import com.example.blog_springboot.modules.notification.viewmodel.NotificationVm;
import com.example.blog_springboot.modules.post.constant.PostConstants;
import com.example.blog_springboot.modules.post.exception.PostNotFoundException;
import com.example.blog_springboot.modules.post.model.Post;
import com.example.blog_springboot.modules.post.repository.PostRepository;
import com.example.blog_springboot.modules.user.model.User;
import com.example.blog_springboot.modules.user.viewmodel.UserDetailVm;
import com.example.blog_springboot.modules.websocket.service.WebSocketService;
import com.example.blog_springboot.utils.Utilities;
import com.fasterxml.jackson.core.JsonProcessingException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class LikePostServiceImpl implements LikePostService {

    private final LikePostRepository likePostRepository;
    private final PostRepository postRepository;
    private final NotificationService notificationService;
    private final UserNotificationService userNotificationService;
    private final WebSocketService webSocketService;

    public LikePostServiceImpl(
            LikePostRepository likePostRepository,
            PostRepository postRepository,
            NotificationService notificationService,
            UserNotificationService userNotificationService,
            WebSocketService webSocketService) {
        this.likePostRepository = likePostRepository;
        this.postRepository = postRepository;
        this.notificationService = notificationService;
        this.userNotificationService = userNotificationService;
        this.webSocketService = webSocketService;

    }

    @Override
    @Transactional
    public SuccessResponse<Boolean> likePost(int postId, User userPrincipal) throws JsonProcessingException {

        var foundPost = postRepository.findById(postId).orElse(null);
        if (foundPost == null) {
            throw new PostNotFoundException(PostConstants.POST_NOT_FOUND);
        }

        var isLiked = likePostRepository.existsByUserAndPost(userPrincipal, foundPost);
        if (isLiked) {
            throw new LikedPostException(LikePostConstants.LIKED_POST);
        }

        LikePost likePost = new LikePost();
        likePost.setUser(userPrincipal);
        likePost.setPost(foundPost);
        likePost.setCreatedAt(new Date());
        likePost.setUpdatedAt(new Date());

        var saveLike = likePostRepository.save(likePost);
        if (saveLike == null) {
            throw new LikePostException(LikePostConstants.LIKE_POST_FAILED);
        }

        if (!foundPost.getUser().getUsername().equals(userPrincipal.getUsername())) {
            CreateNotificationDTO notification = new CreateNotificationDTO();
            notification.setLink("/bai-viet/" + foundPost.getSlug());
            notification.setMessage("Người dùng @" + userPrincipal.getUsername() + " vừa thích bài viết "
                    + foundPost.getTitle() + " của bạn");

            var newNotification = notificationService.createNotification(notification);

            var foundUserByPost = foundPost.getUser();
            // sau đó nối thông báo với user
            List<User> users = new ArrayList<>();
            users.add(foundUserByPost);
            userNotificationService.createUserNotification(users, newNotification);

            // Tạo ra 1 notification view model và gửi tới client
            NotificationVm notificationVm = new NotificationVm();
            notificationVm.setId(newNotification.getId());
            notificationVm.setLink(newNotification.getLink());
            notificationVm.setMessage(newNotification.getMessage());
            notificationVm.setRead(false);
            notificationVm.setCreatedAt(newNotification.getCreatedAt().toString());

            webSocketService.sendNotificationToClient(foundUserByPost.getUsername(), notificationVm);
        }

        return new SuccessResponse<>(LikePostConstants.LIKE_POST_SUCCESS, true);
    }

    @Override
    public SuccessResponse<Boolean> unLikePost(int postId, User userPrincipal) {
        var foundPost = postRepository.findById(postId).orElse(null);
        if (foundPost == null) {
            throw new PostNotFoundException(PostConstants.POST_NOT_FOUND);
        }

        var isLiked = likePostRepository.findByUserAndPost(userPrincipal, foundPost).orElse(null);
        if (isLiked == null) {
            throw new NotYetLikedPostException(LikePostConstants.NOT_YET_LIKED_POST);
        }

        likePostRepository.delete(isLiked);

        return new SuccessResponse<>(LikePostConstants.UNLIKE_POST_SUCCESS, true);

    }

    @Override
    public SuccessResponse<Boolean> checkUserLikedPost(String postSlug, User userPrincipal) {
        var foundPost = postRepository.findBySlug(postSlug).orElse(null);
        if (foundPost == null) {
            throw new PostNotFoundException(PostConstants.POST_NOT_FOUND);
        }

        var isLiked = likePostRepository.existsByUserAndPost(userPrincipal, foundPost);
        if (isLiked) {
            return new SuccessResponse<>("Thành công", true);
        }

        return new SuccessResponse<>("Thành công", false);
    }

    @Override
    public SuccessResponse<List<UserDetailVm>> getListUserLikedPost(String slug) {
        var foundPost = postRepository.findBySlug(slug).orElse(null);
        if (foundPost == null) {
            throw new PostNotFoundException(PostConstants.POST_NOT_FOUND);
        }

        var list = likePostRepository.findByPost(foundPost);
        List<UserDetailVm> result = list.stream().map(item -> {
            var user = item.getUser();
            return Utilities.getUserDetailVm(user);
        }).toList();

        return new SuccessResponse<>("Thành công", result);
    }

    @Override
    public long countLikePost(Post post) {
        return likePostRepository.getTotalLike(post);
    }
}
