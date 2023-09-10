package com.example.blog_springboot.modules.tag.repository;

import com.example.blog_springboot.modules.tag.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag,Integer> {

    Optional<Tag> findBySlug(String slug);
}
