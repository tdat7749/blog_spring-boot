package com.example.blog_springboot.modules.tag.repository;

import com.example.blog_springboot.modules.tag.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag,Integer> {
}
