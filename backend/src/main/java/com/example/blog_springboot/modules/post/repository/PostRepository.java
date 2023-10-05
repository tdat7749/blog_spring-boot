package com.example.blog_springboot.modules.post.repository;

import com.example.blog_springboot.modules.post.model.Post;
import com.example.blog_springboot.modules.user.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface PostRepository extends JpaRepository<Post,Integer> {
    Optional<Post> findBySlug(String slug);
    Optional<Post> findByUserAndId(User user,int id);

    boolean existsByUserAndId(User user,int id);



    //for client
    @Query("select p from Post as p left join p.tags as t where t.slug = :slug and p.isPublished = true")
    Page<Post> getPostByTagSlug(String slug, Pageable paging);

    @Query("select p from Post as p where p.slug = :slug and p.isPublished = true")
    Optional<Post> getPostBySlug(String slug);

    Page<Post> findAllByPublished(boolean isPublished,Pageable paging);

    @Query("select p from Post as p left join p.user as u where u.userName = :userName and p.isPublished = true")
    Page<Post> getAllPostByUsername(String userName,Pageable paging);

    @Query("select p from Post as p where p.isPublished = false")
    Page<Post> getAllPostNotPublished(Pageable paging);
}
