package com.example.blog_springboot.modules.series.Service;


import com.example.blog_springboot.commons.SuccessResponse;
import com.example.blog_springboot.exceptions.NotFoundException;
import com.example.blog_springboot.modules.post.ViewModel.PostListVm;
import com.example.blog_springboot.modules.series.DTO.CreateSeriesDTO;
import com.example.blog_springboot.modules.series.DTO.UpdateSeriesDTO;
import com.example.blog_springboot.modules.series.Exception.CreateSeriesException;
import com.example.blog_springboot.modules.series.Exception.DeleteSeriesException;
import com.example.blog_springboot.modules.series.Exception.UpdateSeriesException;
import com.example.blog_springboot.modules.series.Model.Series;
import com.example.blog_springboot.modules.series.Repository.SeriesRepository;
import com.example.blog_springboot.modules.series.ViewModel.SeriesVm;
import com.example.blog_springboot.modules.user.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeriesServiceImpl implements SeriesService{

    private final SeriesRepository seriesRepository;
    private final UserRepository userRepository;

    public SeriesServiceImpl(SeriesRepository seriesRepository,UserRepository userRepository){
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

        return new SuccessResponse<>("Thành công !",seriesVm);
    }

    @Override
    public SuccessResponse<SeriesVm> createSeries(CreateSeriesDTO dto) {
        var foundUser = userRepository.findById(dto.getUserId()).orElse(null);
        if(foundUser == null){
            throw new NotFoundException("Không tìm thấy user với ID = " + dto.getUserId());
        }
        var newSeries = new Series();
        newSeries.setTitle(dto.getTitle());
        newSeries.setUser(foundUser);

        var saveSeries = seriesRepository.save(newSeries);
        if(saveSeries == null){
            throw new CreateSeriesException("Tạo series thất bại");
        }

        var result = new SeriesVm();
        result.setTitle(saveSeries.getTitle());
        result.setId(saveSeries.getId());
        return new SuccessResponse<>("Thành công !",result);
    }

    @Override
    public SuccessResponse<Boolean> deleteSeries(int id) {
        var foundSeries = seriesRepository.findById(id).orElse(null);
        if(foundSeries == null){
            throw new NotFoundException("Không tìm thấy series với ID = " + id);
        }
        var isDelete = seriesRepository.deleteById(foundSeries.getId());
        if(!isDelete){
            throw new DeleteSeriesException("Xóa series thất bại.");
        }
        return new SuccessResponse<>("Thành công !", true);
    }

    @Override
    public SuccessResponse<SeriesVm> updateSeries(UpdateSeriesDTO dto,int seriesId) {
        var foundSeries = seriesRepository.findById(seriesId).orElse(null);
        if(foundSeries == null){
            throw new NotFoundException("Không tìm thấy series với ID = " + seriesId);
        }
        foundSeries.setTitle(dto.getTitle());

        var isUpdate = seriesRepository.save(foundSeries);
        if(isUpdate == null){
            throw new UpdateSeriesException("Cập nhật series thất bại");
        }

        var result = new SeriesVm();
        result.setId(isUpdate.getId());
        result.setTitle(isUpdate.getTitle());

        return new SuccessResponse<>("Thành công !",result);
    }

    @Override
    public SuccessResponse<List<PostListVm>> getListPostBySeries(int id) {
        return null;
    }
}