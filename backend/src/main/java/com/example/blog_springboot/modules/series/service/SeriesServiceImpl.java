package com.example.blog_springboot.modules.series.service;


import com.example.blog_springboot.commons.PagingResponse;
import com.example.blog_springboot.commons.SuccessResponse;
import com.example.blog_springboot.modules.series.constant.SeriesConstants;
import com.example.blog_springboot.modules.series.dto.CreateSeriesDTO;
import com.example.blog_springboot.modules.series.dto.UpdateSeriesDTO;
import com.example.blog_springboot.modules.series.exception.*;
import com.example.blog_springboot.modules.series.model.Series;
import com.example.blog_springboot.modules.series.repository.SeriesRepository;
import com.example.blog_springboot.modules.series.viewmodel.SeriesListPostVm;
import com.example.blog_springboot.modules.series.viewmodel.SeriesVm;
import com.example.blog_springboot.modules.user.enums.Role;
import com.example.blog_springboot.modules.user.model.User;
import com.example.blog_springboot.modules.user.repository.UserRepository;
import com.example.blog_springboot.utils.Utilities;
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


    public SeriesServiceImpl(SeriesRepository seriesRepository){
        this.seriesRepository = seriesRepository;
    }

    @Override
    public SuccessResponse<SeriesListPostVm> getSeriesDetail(String slug,User user){
        var series = seriesRepository.findByUserAndSlug(user, slug).orElse(null);
        if(series == null){
            throw new SeriesNotFoundException(SeriesConstants.SERIES_NOT_FOUND);
        }
        var seriesVm = Utilities.getSeriesListPostVm(series);

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

        var result = Utilities.getSeriesVm(saveSeries);
        return new SuccessResponse<>(SeriesConstants.CREATE_SERIES_SUCCESS,result);
    }

    @Override
    public SuccessResponse<Boolean> deleteSeries(int id,User userPrincipal) {
        if(!(userPrincipal.getRole() == Role.ADMIN)){
            var isAuthor = seriesRepository.existsByUserAndId(userPrincipal,id);
            if(!isAuthor){
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
            var isAuthor = seriesRepository.existsByUserAndId(userPrincipal,seriesId);
            if(!isAuthor){
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

        var result = Utilities.getSeriesVm(isUpdate);

        return new SuccessResponse<>(SeriesConstants.UPDATE_SERIES_SUCCESS,result);
    }

    @Override
    public SuccessResponse<SeriesListPostVm> getSeriesDetail(String slug) {
        var series = seriesRepository.getSeriesDetail(slug).orElse(null);
        if(series == null){
            throw new SeriesNotFoundException(SeriesConstants.SERIES_NOT_FOUND);
        }

        var result = Utilities.getSeriesListPostVm(series);
        return new SuccessResponse<>("Thành công",result);
    }


    @Override
    public SuccessResponse<PagingResponse<List<SeriesVm>>> getAllSeries(String keyword,String sortBy,int pageIndex) {
        Pageable paging = PageRequest.of(pageIndex, Constants.PAGE_SIZE, Sort.by(Sort.Direction.DESC,sortBy));

        Page<Series> pagingResult = seriesRepository.getAllSeries(keyword,paging);

        List<SeriesVm> listSeriesVm = pagingResult.getContent().stream().map(Utilities::getSeriesVm).toList();

        var result = new PagingResponse<>(pagingResult.getTotalPages(), (int) pagingResult.getTotalElements(),listSeriesVm);

        return new SuccessResponse<>("Thành công",result);

    }

    @Override
    public SuccessResponse<List<SeriesVm>> getListSeriesByUserPrincipal(User userPrincipal) {
        var listSeries = seriesRepository.getByUser(userPrincipal);
        var listSeriesVm = listSeries.stream().map(Utilities::getSeriesVm).toList();

        return new SuccessResponse<>("Thành công",listSeriesVm);
    }

    @Override
    public SuccessResponse<List<SeriesVm>> getListSeriesByUserName(String userName) {
        var listSeries = seriesRepository.getListSeriesByUserName(userName);
        var listSeriesVm = listSeries.stream().map(Utilities::getSeriesVm).toList();

        return new SuccessResponse<>("Thành công",listSeriesVm);
    }

}
