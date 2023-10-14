package com.example.blog_springboot.modules.series.repository;

import com.example.blog_springboot.modules.series.model.Series;
import com.example.blog_springboot.modules.series.viewmodel.SeriesListPostVm;
import com.example.blog_springboot.modules.series.viewmodel.SeriesVm;
import com.example.blog_springboot.modules.user.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface SeriesRepository extends JpaRepository<Series,Integer> {

    Optional<Series> findBySlug(String slug);

    @Query("SELECT DISTINCT s FROM Series s LEFT JOIN s.posts p  WHERE s.slug = :slug AND p.isPublished = true")

    Optional<Series> getSeriesDetail(@Param("slug") String slug);

    boolean existsByUserAndId(User user,int id);

    List<Series> findByUser(User user);


    @Query("SELECT DISTINCT s from Series as s LEFT JOIN s.user as u WHERE u.userName = :userName")
    List<Series> getListSeriesByUserName(String userName);

}
