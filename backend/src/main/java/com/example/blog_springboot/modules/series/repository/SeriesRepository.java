package com.example.blog_springboot.modules.series.repository;

import com.example.blog_springboot.modules.post.model.Post;
import com.example.blog_springboot.modules.series.model.Series;
import com.example.blog_springboot.modules.series.viewmodel.SeriesListPostVm;
import com.example.blog_springboot.modules.series.viewmodel.SeriesVm;
import com.example.blog_springboot.modules.user.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface SeriesRepository extends JpaRepository<Series,Integer> {

    Optional<Series> findBySlug(String slug);

    @Query("SELECT s FROM Series s WHERE s.slug = :slug")

    Optional<Series> getSeriesDetail(@Param("slug") String slug);

    Optional<Series> findByUserAndSlug(User user,String slug);

    boolean existsByUserAndId(User user,int id);


    @Query("SELECT s from Series as s where s.user = :user ORDER BY s.createdAt desc")
    List<Series> getByUser(User user);


    @Query("SELECT DISTINCT s from Series as s LEFT JOIN s.user as u WHERE u.userName = :userName")
    List<Series> getListSeriesByUserName(String userName);

    @Query("SELECT s from Series as s where s.title LIKE %:keyword%")
    Page<Series> getAllSeries(String keyword,Pageable paging);


    @Query("select count(s.id) from Series as s")
    int getTotalSeries();

}
