package com.example.blog_springboot.modules.comment.repository;

import com.example.blog_springboot.modules.comment.model.Comment;
import com.example.blog_springboot.modules.post.model.Post;
import com.example.blog_springboot.modules.user.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    boolean existsByUserAndId(User user, int id);

    @Query("select count(c.id) from Comment as c where c.post = :post")
    long getTotalComment(Post post);

    @Query("Select c from Comment as c where c.post = :post and c.parentId = null order by c.createdAt desc")
    Page<Comment> getListCommentByPost(Post post, Pageable paging);

    @Query("select c from Comment as c where c.parentId = :parentId")
    List<Comment> getListChildCommentByParent(int parentId);
}
