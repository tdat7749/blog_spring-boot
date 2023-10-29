package com.example.blog_springboot.modules.tag.repository;

import com.example.blog_springboot.modules.tag.model.Tag;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag, Integer> {

    Optional<Tag> findBySlug(String slug);

    @Query("select t from Tag as t where t.title LIKE %:keyword% order by t.createdAt desc")
    Page<Tag> getListTag(String keyword, Pageable paging);
}
