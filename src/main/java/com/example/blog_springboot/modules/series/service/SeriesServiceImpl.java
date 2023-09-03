package com.example.blog_springboot.modules.series.service;


import com.example.blog_springboot.commons.SuccessResponse;
import com.example.blog_springboot.exceptions.NotFoundException;
import com.example.blog_springboot.modules.post.ViewModel.PostListVm;
import com.example.blog_springboot.modules.series.dto.CreateSeriesDTO;
import com.example.blog_springboot.modules.series.dto.UpdateSeriesDTO;
import com.example.blog_springboot.modules.series.exception.*;
import com.example.blog_springboot.modules.series.model.Series;
import com.example.blog_springboot.modules.series.repository.SeriesRepository;
import com.example.blog_springboot.modules.series.viewmodel.SeriesVm;
import com.example.blog_springboot.modules.user.enums.Role;
import com.example.blog_springboot.modules.user.model.User;
import com.example.blog_springboot.modules.user.repository.UserRepository;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class SeriesServiceImpl implements SeriesService{

    private final SeriesRepository seriesRepository;
    private final UserRepository userRepository;


    public SeriesServiceImpl(SeriesRepository seriesRepository, UserRepository userRepository){
        this.seriesRepository = seriesRepository;
        this.userRepository = userRepository;
    }

    @Override
    public SuccessResponse<SeriesVm> getSeriesById(int id){
        var series = seriesRepository.findById(id).orElse(null);
        if(series == null){
            throw new NotFoundException("Không tìm thấy series với ID = " + id);
        }
        var seriesVm = new SeriesVm();
        seriesVm.setId(series.getId());
        seriesVm.setTitle(series.getTitle());
        seriesVm.setSlug(series.getSlug());
        seriesVm.setContent(series.getContent());
        seriesVm.setCreatedAt(series.getCreatedAt().toString());
        seriesVm.setUpdatedAt(series.getUpdatedAt().toString());

        return new SuccessResponse<>("Thành công !",seriesVm);
    }

    @Override
    public SuccessResponse<SeriesVm> createSeries(CreateSeriesDTO dto) {
        var foundUser = userRepository.findById(dto.getUserId()).orElse(null);
        if(foundUser == null){
            throw new NotFoundException("Không tìm thấy user với ID = " + dto.getUserId());
        }

        var foundSeriesBySlug = seriesRepository.findBySlug(dto.getSlug()).orElse(null);
        if(foundSeriesBySlug != null){
            throw new SeriesSlugDuplicateException("Tiêu đề này đã bị trùng");
        }

        var newSeries = new Series();
        newSeries.setTitle(dto.getTitle());
        newSeries.setSlug(dto.getSlug());
        newSeries.setContent(dto.getContent());
        newSeries.setCreatedAt(new Date());
        newSeries.setUpdatedAt(new Date());
        newSeries.setUser(foundUser);

        var saveSeries = seriesRepository.save(newSeries);
        if(saveSeries == null){
            throw new CreateSeriesException("Tạo series thất bại");
        }

        var result = new SeriesVm();
        result.setTitle(saveSeries.getTitle());
        result.setId(saveSeries.getId());
        result.setSlug(saveSeries.getSlug());
        result.setContent(saveSeries.getContent());
        result.setCreatedAt(saveSeries.getCreatedAt().toString());
        result.setUpdatedAt(saveSeries.getUpdatedAt().toString());
        return new SuccessResponse<>("Thành công !",result);
    }

    @Override
    public SuccessResponse<Boolean> deleteSeries(int id,User userPrincipal) {
        var foundSeries = seriesRepository.findById(id).orElse(null);
        if(foundSeries == null){
            throw new NotFoundException("Không tìm thấy series với ID = " + id);
        }

        if(!(userPrincipal.getRole() == Role.ADMIN)){
            var isAuthor = seriesRepository.findByUserAndId(userPrincipal,id).orElse(null);
            if(isAuthor == null){
                throw new NotAuthorSeriesException("Bạn không phải là chủ của series này");
            }
        }

        seriesRepository.delete(foundSeries);
        return new SuccessResponse<>("Thành công !", true);
    }

    @Override
    public SuccessResponse<SeriesVm> updateSeries(UpdateSeriesDTO dto,int seriesId) {
        var foundSeries = seriesRepository.findById(seriesId).orElse(null);
        if(foundSeries == null){
            throw new NotFoundException("Không tìm thấy series với ID = " + seriesId);
        }

        var foundSeriesBySlug = seriesRepository.findBySlug(dto.getSlug()).orElse(null);
        if(foundSeriesBySlug != null && foundSeries != foundSeriesBySlug){
                throw new SeriesSlugDuplicateException("Tiêu đề này đã bị trùng");
        }

        foundSeries.setTitle(dto.getTitle());
        foundSeries.setUpdatedAt(new Date());
        foundSeries.setSlug(dto.getSlug());
        foundSeries.setContent(dto.getContent());

        var isUpdate = seriesRepository.save(foundSeries);
        if(isUpdate == null){
            throw new UpdateSeriesException("Cập nhật series thất bại");
        }

        var result = new SeriesVm();
        result.setId(isUpdate.getId());
        result.setTitle(isUpdate.getTitle());
        result.setSlug(isUpdate.getSlug());
        result.setContent(isUpdate.getContent());
        result.setCreatedAt(isUpdate.getCreatedAt().toString());
        result.setUpdatedAt(isUpdate.getUpdatedAt().toString());

        return new SuccessResponse<>("Thành công !",result);
    }

    @Override
    public SuccessResponse<List<PostListVm>> getListPostBySeries(int id) {
        return null;
    }
}
