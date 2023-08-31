package com.example.blog_springboot.modules.series.Controller;

import com.example.blog_springboot.commons.SuccessResponse;
import com.example.blog_springboot.modules.series.DTO.CreateSeriesDTO;
import com.example.blog_springboot.modules.series.DTO.UpdateSeriesDTO;
import com.example.blog_springboot.modules.series.Model.Series;
import com.example.blog_springboot.modules.series.Service.SeriesService;
import com.example.blog_springboot.modules.series.ViewModel.SeriesVm;
import jakarta.validation.Valid;
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
    public ResponseEntity<SuccessResponse<SeriesVm>> createSeries(@RequestBody @Valid CreateSeriesDTO dto){
        var result = seriesService.createSeries(dto);

        return new ResponseEntity<>(result,HttpStatus.CREATED);
    }

    @PutMapping("{seriesId}")
    @ResponseBody
    public ResponseEntity<SuccessResponse<SeriesVm>> updateSeries(@RequestBody @Valid UpdateSeriesDTO dto,@PathVariable("seriesId") int id){
        var result = seriesService.updateSeries(dto,id);

        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    @ResponseBody
    public ResponseEntity<SuccessResponse<Boolean>> deleteSeries(@PathVariable int id){
        var result = seriesService.deleteSeries(id);

        return new ResponseEntity<>(result,HttpStatus.OK);
    }
}
