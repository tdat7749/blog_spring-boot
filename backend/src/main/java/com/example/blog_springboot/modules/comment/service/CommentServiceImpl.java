package com.example.blog_springboot.modules.comment.service;

import com.example.blog_springboot.commons.Constants;
import com.example.blog_springboot.commons.PagingResponse;
import com.example.blog_springboot.commons.SuccessResponse;
import com.example.blog_springboot.modules.comment.constant.CommentConstants;
import com.example.blog_springboot.modules.comment.dto.CreateCommentDTO;
import com.example.blog_springboot.modules.comment.dto.EditCommentDTO;
import com.example.blog_springboot.modules.comment.exception.CommentNotFoundException;
import com.example.blog_springboot.modules.comment.exception.CreateCommentException;
import com.example.blog_springboot.modules.comment.exception.NotAuthorCommentException;
import com.example.blog_springboot.modules.comment.exception.UpdateCommentException;
import com.example.blog_springboot.modules.comment.model.Comment;
import com.example.blog_springboot.modules.comment.repository.CommentRepository;
import com.example.blog_springboot.modules.comment.viewmodel.CommentVm;
import com.example.blog_springboot.modules.notification.dto.CreateNotificationDTO;
import com.example.blog_springboot.modules.notification.service.NotificationService;
import com.example.blog_springboot.modules.notification.service.UserNotificationService;
import com.example.blog_springboot.modules.notification.viewmodel.NotificationVm;
import com.example.blog_springboot.modules.post.constant.PostConstants;
import com.example.blog_springboot.modules.post.exception.PostNotFoundException;
import com.example.blog_springboot.modules.post.model.Post;
import com.example.blog_springboot.modules.post.repository.PostRepository;
import com.example.blog_springboot.modules.user.model.User;
import com.example.blog_springboot.modules.websocket.service.WebSocketService;
import com.example.blog_springboot.utils.Utilities;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    private final WebSocketService webSocketService;

    private final NotificationService notificationService;

    private final UserNotificationService userNotificationService;

    public CommentServiceImpl(
            CommentRepository commentRepository,
            PostRepository postRepository,
            NotificationService notificationService,
            UserNotificationService userNotificationService,
            WebSocketService webSocketService) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.notificationService = notificationService;
        this.userNotificationService = userNotificationService;
        this.webSocketService = webSocketService;
    }

    @Override
    @Transactional
    public SuccessResponse<CommentVm> createComment(CreateCommentDTO dto, int postId, User user)
            throws JsonProcessingException {
        var foundPost = postRepository.findById(postId).orElse(null);
        if (foundPost == null) {
            throw new PostNotFoundException(PostConstants.POST_NOT_FOUND);
        }

        Comment newComment = new Comment();
        newComment.setPost(foundPost);
        newComment.setContent(dto.getContent());
        newComment.setUser(user);
        newComment.setCreatedAt(new Date());
        newComment.setUpdatedAt(new Date());
        if (dto.getParentId() != null) {
            var foundComment = commentRepository.findById(dto.getParentId()).orElse(null);
            if (foundComment == null) {
                throw new CommentNotFoundException(CommentConstants.COMMENT_NOT_FOUND);
            }
            newComment.setParentId(dto.getParentId());

            var foundUserByComment = foundComment.getUser();
            if (!foundUserByComment.getUsername().equals(user.getUsername())) {
                // sau khi khởi tạo follow thành công thì tạo ra 1 thông báo
                CreateNotificationDTO notification = new CreateNotificationDTO();
                notification.setLink("/bai-viet/" + foundPost.getSlug());
                notification.setMessage("Người dùng @" + user.getUsername() + " vừa trả lời bình luận của bạn");

                var newNotification = notificationService.createNotification(notification);

                // sau đó nối thông báo với user
                List<User> users = new ArrayList<>();
                users.add(foundUserByComment);
                userNotificationService.createUserNotification(users, newNotification);

                // Tạo ra 1 notification view model và gửi tới client
                NotificationVm notificationVm = new NotificationVm();
                notificationVm.setId(newNotification.getId());
                notificationVm.setLink(newNotification.getLink());
                notificationVm.setMessage(newNotification.getMessage());
                notificationVm.setRead(false);
                notificationVm.setCreatedAt(newNotification.getCreatedAt().toString());

                webSocketService.sendNotificationToClient(foundUserByComment.getUsername(), notificationVm);
            }
        }

        var save = commentRepository.save(newComment);

        if (save == null) {
            throw new CreateCommentException(CommentConstants.CREATE_COMMENT_FAILED);
        }

        if (dto.getParentId() == null) {
            CreateNotificationDTO notification = new CreateNotificationDTO();
            notification.setLink("/bai-viet/" + foundPost.getSlug());
            notification.setMessage("Người dùng @" + user.getUsername() + " vừa bình luận trong bài viết "
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

        var commentVm = Utilities.getCommentVM(save);
        return new SuccessResponse<>(CommentConstants.CREATE_COMMENT_SUCCESS, commentVm);
    }

    @Override
    public SuccessResponse<CommentVm> editComment(EditCommentDTO dto, User user) {
        var isAuthor = commentRepository.existsByUserAndId(user, dto.getId());
        if (!isAuthor) {
            throw new NotAuthorCommentException(CommentConstants.NOT_AUTHOR_COMMENT);
        }

        var foundComment = commentRepository.findById(dto.getId()).orElse(null);
        if (foundComment == null) {
            throw new CommentNotFoundException(CommentConstants.COMMENT_NOT_FOUND);
        }

        foundComment.setContent(dto.getContent());
        foundComment.setCreatedAt(new Date());

        var save = commentRepository.save(foundComment);

        if (save == null) {
            throw new UpdateCommentException(CommentConstants.EDIT_COMMENT_FAILED);
        }

        var commentVm = Utilities.getCommentVM(save);
        return new SuccessResponse<>(CommentConstants.EDIT_COMMENT_SUCCESS, commentVm);
    }

    @Override
    @Transactional
    public SuccessResponse<Boolean> deleteComment(int id, User user) {
        var isAuthor = commentRepository.existsByUserAndId(user, id);
        if (!isAuthor) {
            throw new NotAuthorCommentException(CommentConstants.NOT_AUTHOR_COMMENT);
        }

        var foundComment = commentRepository.findById(id).orElse(null);
        if (foundComment == null) {
            throw new CommentNotFoundException(CommentConstants.COMMENT_NOT_FOUND);
        }

        if (foundComment.getParentId() == null) {
            var listChild = commentRepository.getListChildCommentByParent(foundComment.getId());
            commentRepository.deleteAll(listChild);
        }

        commentRepository.delete(foundComment);

        return new SuccessResponse<>(CommentConstants.DELETE_COMMENT_SUCCESS, true);
    }

    @Override
    public SuccessResponse<PagingResponse<List<CommentVm>>> getListCommentPost(String slug, String sortBy,
            int pageIndex) {
        var foundPost = postRepository.findBySlug(slug).orElse(null);
        if (foundPost == null) {
            throw new PostNotFoundException(PostConstants.POST_NOT_FOUND);
        }

        Pageable paging = PageRequest.of(pageIndex, Constants.PAGE_SIZE, Sort.by(sortBy));

        var pagingResult = commentRepository.getListCommentByPost(foundPost, paging);

        var listCommentVm = pagingResult.stream().map(this::getCommentVmWithChild).toList();

        return new SuccessResponse<>("Thành công!", new PagingResponse<>(pagingResult.getTotalPages(),
                (int) pagingResult.getTotalElements(), listCommentVm));

    }

    @Override
    public long countCommentPost(Post post) {
        return commentRepository.getTotalComment(post);
    }

    private CommentVm getCommentVmWithChild(Comment cm) {
        var commentVm = new CommentVm();
        commentVm.setId(cm.getId());
        commentVm.setContent(cm.getContent());
        commentVm.setCreatedAt(cm.getCreatedAt().toString());

        var userVm = Utilities.getUserVm(cm.getUser());

        commentVm.setUser(userVm);

        // get list child
        var child = commentRepository.getListChildCommentByParent(cm.getId());

        var listChildVm = child.stream().map(Utilities::getCommentVM).toList();

        commentVm.setChildComment(listChildVm);
        return commentVm;
    }
}
