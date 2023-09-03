package com.example.blog_springboot.modules.series.repository;

import com.example.blog_springboot.modules.series.model.Series;
import com.example.blog_springboot.modules.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface SeriesRepository extends JpaRepository<Series,Integer> {

    Optional<Series> findBySlug(String slug);
    Optional<Series> findByUserAndId(User user,int id);
}
