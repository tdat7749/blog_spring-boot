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

import java.util.Optional;


@Repository
public interface SeriesRepository extends JpaRepository<Series,Integer> {

    Optional<Series> findBySlug(String slug);
    Optional<Series> findByUserAndId(User user,int id);

//    @Query("Select distinct s FROM Series s left join s.posts p left join p.postTags pt inner join pt.tag t" +
//            " where s.slug = :slug and p.isPublished = true")

    @Query("SELECT DISTINCT s, p, pt.tag FROM Series s LEFT JOIN s.posts p LEFT JOIN p.postTags pt WHERE s.slug = :slug AND p.isPublished = true")

    Optional<Series> getListPostBySeries(@Param("slug") String slug);


}
