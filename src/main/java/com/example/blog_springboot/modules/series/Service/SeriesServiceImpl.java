package com.example.blog_springboot.modules.series.Service;


import com.example.blog_springboot.commons.SuccessResponse;
import com.example.blog_springboot.exceptions.NotFoundException;
import com.example.blog_springboot.modules.series.DTO.CreateSeriesDTO;
import com.example.blog_springboot.modules.series.Exception.CreateSeriesException;
import com.example.blog_springboot.modules.series.Model.Series;
import com.example.blog_springboot.modules.series.Repository.SeriesRepository;
import com.example.blog_springboot.modules.series.ViewModel.SeriesVm;
import com.example.blog_springboot.modules.user.Repository.UserRepository;
import org.springframework.stereotype.Service;

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
    public SuccessResponse<Series> createSeries(CreateSeriesDTO dto) {
        var foundUser = userRepository.findById(dto.getUserId()).orElse(null);
        if(foundUser == null){
            throw new NotFoundException("Không tìm thấy user với ID = " + dto.getUserId());
        }
        var newSeries = new Series();
        newSeries.setTitle(dto.getTitle());
        newSeries.setUser(foundUser);

        var result = seriesRepository.save(newSeries);
        if(result == null){
            throw new CreateSeriesException("Tạo series thất bại");
        }
        return new SuccessResponse<>("Thành công !",result);
    }
}
