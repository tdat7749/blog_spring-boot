package com.example.blog_springboot.utils;

import com.example.blog_springboot.modules.comment.model.Comment;
import com.example.blog_springboot.modules.comment.service.CommentService;
import com.example.blog_springboot.modules.comment.viewmodel.CommentVm;
import com.example.blog_springboot.modules.likepost.service.LikePostService;
import com.example.blog_springboot.modules.notification.model.UserNotification;
import com.example.blog_springboot.modules.notification.viewmodel.NotificationVm;
import com.example.blog_springboot.modules.notification.viewmodel.RpNotificationVm;
import com.example.blog_springboot.modules.post.model.Post;
import com.example.blog_springboot.modules.post.viewmodel.PostListVm;
import com.example.blog_springboot.modules.post.viewmodel.PostVm;
import com.example.blog_springboot.modules.series.model.Series;
import com.example.blog_springboot.modules.series.viewmodel.SeriesListPostVm;
import com.example.blog_springboot.modules.series.viewmodel.SeriesVm;
import com.example.blog_springboot.modules.tag.model.Tag;
import com.example.blog_springboot.modules.tag.viewmodel.TagVm;
import com.example.blog_springboot.modules.user.model.User;
import com.example.blog_springboot.modules.user.viewmodel.UserDetailVm;
import com.example.blog_springboot.modules.user.viewmodel.UserVm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

@Component
public class Utilities {

    private static CommentService commentService;
    private static LikePostService likePostService;

    public Utilities(CommentService commentService, LikePostService likePostService) {
        Utilities.commentService = commentService;
        Utilities.likePostService = likePostService;
    }

    public static String generateCode() {
        Random rnd = new Random();
        int number = rnd.nextInt(999999);

        return String.format("%06d", number);
    }

    public static UserDetailVm getUserDetailVm(User user) {
        UserDetailVm userVm = new UserDetailVm();
        userVm.setAvatar(user.getAvatar());
        userVm.setFirstName(user.getFirstName());
        userVm.setLastName(user.getLastName());
        userVm.setEmail(user.getEmail());
        userVm.setNotLocked(user.isAccountNonLocked());
        userVm.setRole(user.getRole().toString());
        userVm.setUserName(user.getUsername());
        userVm.setId(user.getId());
        userVm.setSummary(user.getSummary());

        return userVm;
    }

    public static UserVm getUserVm(User user) {
        UserVm userVm = new UserVm();
        userVm.setAvatar(user.getAvatar());
        userVm.setFirstName(user.getFirstName());
        userVm.setLastName(user.getLastName());
        userVm.setUserName(user.getUsername());
        userVm.setId(user.getId());

        return userVm;
    }

    public static TagVm getTagVm(Tag tag) {
        TagVm tagVm = new TagVm();
        tagVm.setId(tag.getId());
        tagVm.setTitle(tag.getTitle());
        tagVm.setSlug(tag.getSlug());
        tagVm.setCreatedAt(tag.getCreatedAt().toString());
        tagVm.setUpdatedAt(tag.getUpdatedAt().toString());

        return tagVm;
    }

    public static SeriesVm getSeriesVm(Series series) {
        var seriesVm = new SeriesVm();
        seriesVm.setId(series.getId());
        seriesVm.setTitle(series.getTitle());
        seriesVm.setSlug(series.getSlug());
        seriesVm.setContent(series.getContent());
        seriesVm.setCreatedAt(series.getCreatedAt().toString());
        seriesVm.setUpdatedAt(series.getUpdatedAt().toString());

        return seriesVm;
    }

    public static SeriesListPostVm getSeriesListPostVm(Series series) {
        var seriesListPostVm = new SeriesListPostVm();
        seriesListPostVm.setId(series.getId());
        seriesListPostVm.setTitle(series.getTitle());
        seriesListPostVm.setSlug(series.getSlug());
        seriesListPostVm.setContent(series.getContent());
        seriesListPostVm.setCreatedAt(series.getCreatedAt().toString());
        seriesListPostVm.setUpdatedAt(series.getUpdatedAt().toString());

        var listPostVm = series.getPosts().stream().map(Utilities::getPostListVm).toList();
        seriesListPostVm.setPosts(listPostVm);

        return seriesListPostVm;
    }

    public static PostVm transferPostVm(Post post) {
        var postVm = new PostVm();

        postVm.setId(post.getId());
        postVm.setTitle(post.getTitle());
        postVm.setSlug(post.getSlug());
        postVm.setSummary(post.getSummary());
        postVm.setThumbnail(post.getThumbnail());
        postVm.setCreatedAt(post.getCreatedAt().toString());
        postVm.setUpdatedAt(post.getUpdatedAt().toString());
        postVm.setTotalView(post.getTotalView());
        postVm.setIsPublished(post.isPublished());
        postVm.setTotalComment(commentService.countCommentPost(post));
        postVm.setTotalLike(likePostService.countLikePost(post));
        if (post.getSeries() != null) {
            postVm.setSeries(getSeriesVm(post.getSeries()));
        }

        return postVm;
    }

    public static PostListVm transferPostListVm(Post post) {
        var postListVm = new PostListVm();

        postListVm.setId(post.getId());
        postListVm.setTitle(post.getTitle());
        postListVm.setSlug(post.getSlug());
        postListVm.setSummary(post.getSummary());
        postListVm.setThumbnail(post.getThumbnail());
        postListVm.setCreatedAt(post.getCreatedAt().toString());
        postListVm.setUpdatedAt(post.getUpdatedAt().toString());
        postListVm.setTotalView(post.getTotalView());
        postListVm.setPublished(post.isPublished());
        postListVm.setTotalComment(commentService.countCommentPost(post));
        postListVm.setTotalLike(likePostService.countLikePost(post));
        if (post.getSeries() != null) {
            postListVm.setSeries(getSeriesVm(post.getSeries()));
        }

        return postListVm;
    }

    public static PostVm getPostVm(Post post) {
        var postVm = transferPostVm(post);
        postVm.setContent(post.getContent());

        // set userVm

        var userVm = getUserVm(post.getUser());

        // set tagVm
        List<TagVm> listTagVm = post.getTags().stream().map(Utilities::getTagVm).toList();

        postVm.setTags(listTagVm);
        postVm.setAuthor(userVm);

        return postVm;
    }

    public static PostListVm getPostListVm(Post post) {
        var postListVm = transferPostListVm(post);

        // set userVm
        var userVm = getUserVm(post.getUser());

        // set tagVm
        List<TagVm> listTagVm = post.getTags().stream().map(Utilities::getTagVm).toList();

        postListVm.setTags(listTagVm);
        postListVm.setAuthor(userVm);

        return postListVm;
    }

    public static NotificationVm getNotificationVm(UserNotification un) {
        NotificationVm vm = new NotificationVm();
        vm.setRead(un.isRead());
        vm.setMessage(un.getNotification().getMessage());
        vm.setLink(un.getNotification().getLink());
        vm.setId(un.getId());
        vm.setCreatedAt(un.getNotification().getCreatedAt().toString());

        return vm;
    }

    public static RpNotificationVm getRpNotificationVm(int unRead, List<NotificationVm> list) {
        RpNotificationVm vm = new RpNotificationVm();
        vm.setUnSeenNotification(unRead);
        vm.setNotifications(list);
        return vm;
    }

    public static CommentVm getCommentVM(Comment cm) {
        var commentVm = new CommentVm();
        commentVm.setId(cm.getId());
        commentVm.setContent(cm.getContent());
        commentVm.setCreatedAt(cm.getCreatedAt().toString());
        if (cm.getParentId() != null) {
            commentVm.setParentId(cm.getParentId());
        }
        var userVm = Utilities.getUserVm(cm.getUser());

        commentVm.setUser(userVm);
        return commentVm;
    }
}
