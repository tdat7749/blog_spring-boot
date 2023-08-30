package com.example.blog_springboot.modules.series.Service;

import com.example.blog_springboot.commons.SuccessResponse;
import com.example.blog_springboot.modules.series.DTO.CreateSeriesDTO;
import com.example.blog_springboot.modules.series.Model.Series;
import com.example.blog_springboot.modules.series.ViewModel.SeriesVm;
import org.springframework.stereotype.Service;

@Service
public interface SeriesService {
    public SuccessResponse<SeriesVm> getSeriesById(int id);

    public SuccessResponse<Series> createSeries(CreateSeriesDTO dto);
}
