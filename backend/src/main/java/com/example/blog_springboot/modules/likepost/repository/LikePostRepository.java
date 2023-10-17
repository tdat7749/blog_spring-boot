package com.example.blog_springboot.modules.likepost.repository;

import com.example.blog_springboot.modules.likepost.model.LikePost;
import com.example.blog_springboot.modules.post.model.Post;
import com.example.blog_springboot.modules.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface LikePostRepository extends JpaRepository<LikePost,Integer> {
    boolean existsByUserAndPost(User user, Post post);

    Optional<LikePost> findByUserAndPost(User user, Post post);

    List<LikePost> findByPost(Post post);

    @Query("SELECT count(lp.id) FROM LikePost as lp where lp.post = :post")
    long getTotalLike(Post post);
}
