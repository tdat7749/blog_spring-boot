package com.example.blog_springboot.modules.series.Service;

import com.example.blog_springboot.commons.SuccessResponse;
import com.example.blog_springboot.modules.post.ViewModel.PostListVm;
import com.example.blog_springboot.modules.series.DTO.CreateSeriesDTO;
import com.example.blog_springboot.modules.series.DTO.UpdateSeriesDTO;
import com.example.blog_springboot.modules.series.Model.Series;
import com.example.blog_springboot.modules.series.ViewModel.SeriesVm;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SeriesService {
    public SuccessResponse<SeriesVm> getSeriesById(int id);

    public SuccessResponse<SeriesVm> createSeries(CreateSeriesDTO dto);
    public SuccessResponse<Boolean> deleteSeries(int id);
    public SuccessResponse<SeriesVm> updateSeries(UpdateSeriesDTO dto,int seriesId);

    public SuccessResponse<List<PostListVm>> getListPostBySeries(int id);
}
