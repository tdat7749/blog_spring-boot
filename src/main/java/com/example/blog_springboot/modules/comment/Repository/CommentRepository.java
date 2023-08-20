package com.example.blog_springboot.modules.comment.Repository;

import com.example.blog_springboot.modules.comment.Model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

}
