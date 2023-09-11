package com.example.blog_springboot.modules.series.service;

import com.example.blog_springboot.commons.PagingRequestDTO;
import com.example.blog_springboot.commons.PagingResponse;
import com.example.blog_springboot.commons.SuccessResponse;
import com.example.blog_springboot.modules.post.ViewModel.PostListVm;
import com.example.blog_springboot.modules.series.dto.CreateSeriesDTO;
import com.example.blog_springboot.modules.series.dto.UpdateSeriesDTO;
import com.example.blog_springboot.modules.series.model.Series;
import com.example.blog_springboot.modules.series.viewmodel.SeriesListPostVm;
import com.example.blog_springboot.modules.series.viewmodel.SeriesVm;
import com.example.blog_springboot.modules.user.model.User;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SeriesService {
    public SuccessResponse<SeriesVm> getSeriesById(int id); // for edit

    public SuccessResponse<SeriesVm> createSeries(CreateSeriesDTO dto);
    public SuccessResponse<Boolean> deleteSeries(int id,User userPrincipal);
    public SuccessResponse<SeriesVm> updateSeries(UpdateSeriesDTO dto,int seriesId,User userPrincipal);

    public SuccessResponse<Series> getSeriesDetail(String slug); // for view
    
    public SuccessResponse<PagingResponse<List<SeriesVm>>> getAllSeries(PagingRequestDTO dto);
}
