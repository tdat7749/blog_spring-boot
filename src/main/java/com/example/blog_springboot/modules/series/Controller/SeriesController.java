package com.example.blog_springboot.modules.series.Controller;

import com.example.blog_springboot.commons.SuccessResponse;
import com.example.blog_springboot.modules.series.DTO.CreateSeriesDTO;
import com.example.blog_springboot.modules.series.Model.Series;
import com.example.blog_springboot.modules.series.Service.SeriesService;
import com.example.blog_springboot.modules.series.ViewModel.SeriesVm;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping(value = "/api/series")
public class SeriesController {
    private final SeriesService seriesService;

    public SeriesController(SeriesService seriesService){
        this.seriesService = seriesService;
    }

    @GetMapping("{id}")
    @ResponseBody
    public ResponseEntity<SuccessResponse<SeriesVm>> getSeriesById(@PathVariable int id){
        var result = seriesService.getSeriesById(id);

        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    @PostMapping("")
    @ResponseBody
    public ResponseEntity<SuccessResponse<Series>> createSeries(@RequestBody CreateSeriesDTO dto){
        var result = seriesService.createSeries(dto);

        return new ResponseEntity<>(result,HttpStatus.OK);
    }
}
