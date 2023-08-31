package com.example.blog_springboot.modules.series.Repository;

import com.example.blog_springboot.modules.series.Model.Series;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SeriesRepository extends JpaRepository<Series,Integer> {
    Boolean deleteById(int Id);
}
