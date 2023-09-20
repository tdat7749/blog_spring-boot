package com.example.blog_springboot.modules.series.service;


import com.example.blog_springboot.commons.PagingResponse;
import com.example.blog_springboot.commons.SuccessResponse;
import com.example.blog_springboot.modules.series.constant.SeriesConstants;
import com.example.blog_springboot.modules.series.dto.CreateSeriesDTO;
import com.example.blog_springboot.modules.series.dto.UpdateSeriesDTO;
import com.example.blog_springboot.modules.series.exception.*;
import com.example.blog_springboot.modules.series.model.Series;
import com.example.blog_springboot.modules.series.repository.SeriesRepository;
import com.example.blog_springboot.modules.series.viewmodel.SeriesVm;
import com.example.blog_springboot.modules.user.enums.Role;
import com.example.blog_springboot.modules.user.model.User;
import com.example.blog_springboot.modules.user.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.example.blog_springboot.commons.Constants;

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
            throw new SeriesNotFoundException(SeriesConstants.SERIES_NOT_FOUND);
        }
        var seriesVm = getSeriesVm(series);

        return new SuccessResponse<>("Thành công !",seriesVm);
    }

    @Override
    public SuccessResponse<SeriesVm> createSeries(CreateSeriesDTO dto,User userPrincipal) {
        var foundSeriesBySlug = seriesRepository.findBySlug(dto.getSlug()).orElse(null);
        if(foundSeriesBySlug != null){
            throw new SeriesSlugDuplicateException(SeriesConstants.SERIES_SLUG_DUPLICATE);
        }

        var newSeries = new Series();
        newSeries.setTitle(dto.getTitle());
        newSeries.setSlug(dto.getSlug());
        newSeries.setContent(dto.getContent());
        newSeries.setCreatedAt(new Date());
        newSeries.setUpdatedAt(new Date());
        newSeries.setUser(userPrincipal);

        var saveSeries = seriesRepository.save(newSeries);
        if(saveSeries == null){
            throw new CreateSeriesException(SeriesConstants.CREATE_SERIES_FAILED);
        }

        var result = getSeriesVm(saveSeries);
        return new SuccessResponse<>(SeriesConstants.CREATE_SERIES_SUCCESS,result);
    }

    @Override
    public SuccessResponse<Boolean> deleteSeries(int id,User userPrincipal) {
        if(!(userPrincipal.getRole() == Role.ADMIN)){
            var isAuthor = seriesRepository.findByUserAndId(userPrincipal,id).orElse(null);
            if(isAuthor == null){
                throw new NotAuthorSeriesException(SeriesConstants.NOT_AUTHOR_SERIES);
            }
        }

        var foundSeries = seriesRepository.findById(id).orElse(null);
        if(foundSeries == null){
            throw new SeriesNotFoundException(SeriesConstants.SERIES_NOT_FOUND);
        }


        seriesRepository.delete(foundSeries);
        return new SuccessResponse<>(SeriesConstants.DELETE_SERIES_SUCCESS, true);
    }

    @Override
    public SuccessResponse<SeriesVm> updateSeries(UpdateSeriesDTO dto,int seriesId,User userPrincipal) {
        if(!(userPrincipal.getRole() == Role.ADMIN)){
            var isAuthor = seriesRepository.findByUserAndId(userPrincipal,seriesId).orElse(null);
            if(isAuthor == null){
                throw new NotAuthorSeriesException(SeriesConstants.NOT_AUTHOR_SERIES);
            }
        }

        var foundSeries = seriesRepository.findById(seriesId).orElse(null);
        if(foundSeries == null){
            throw new SeriesNotFoundException(SeriesConstants.NOT_AUTHOR_SERIES);
        }

        var foundSeriesBySlug = seriesRepository.findBySlug(dto.getSlug()).orElse(null);
        if(foundSeriesBySlug != null && foundSeries != foundSeriesBySlug){
                throw new SeriesSlugDuplicateException(SeriesConstants.SERIES_SLUG_DUPLICATE);
        }

        foundSeries.setTitle(dto.getTitle());
        foundSeries.setUpdatedAt(new Date());
        foundSeries.setSlug(dto.getSlug());
        foundSeries.setContent(dto.getContent());

        var isUpdate = seriesRepository.save(foundSeries);
        if(isUpdate == null){
            throw new UpdateSeriesException(SeriesConstants.UPDATE_SERIES_FAILED);
        }

        var result = getSeriesVm(isUpdate);

        return new SuccessResponse<>(SeriesConstants.UPDATE_SERIES_SUCCESS,result);
    }

    @Override
    public SuccessResponse<Series> getSeriesDetail(String slug) {
        var series = seriesRepository.getSeriesDetail(slug).orElse(null);
        if(series == null){
            throw new SeriesNotFoundException(SeriesConstants.SERIES_NOT_FOUND);
        }
        return new SuccessResponse<>("Thành công",series);
    }


    @Override
    public SuccessResponse<PagingResponse<List<SeriesVm>>> getAllSeries(String sortBy,int pageIndex) {
        Pageable paging = PageRequest.of(pageIndex, Constants.PAGE_SIZE, Sort.by(sortBy));

        Page<Series> pagingResult = seriesRepository.findAll(paging);

        List<SeriesVm> listSeriesVm = pagingResult.getContent().stream().map(this::getSeriesVm).toList();

        var result = new PagingResponse<>(pagingResult.getTotalPages(), (int) pagingResult.getTotalElements(),listSeriesVm);

        return new SuccessResponse<>("Thành công",result);

    }

    private SeriesVm getSeriesVm(Series series){
        var seriesVm = new SeriesVm();
        seriesVm.setId(series.getId());
        seriesVm.setTitle(series.getTitle());
        seriesVm.setSlug(series.getSlug());
        seriesVm.setContent(series.getContent());
        seriesVm.setCreatedAt(series.getCreatedAt().toString());
        seriesVm.setUpdatedAt(series.getUpdatedAt().toString());

        return seriesVm;
    }
}
