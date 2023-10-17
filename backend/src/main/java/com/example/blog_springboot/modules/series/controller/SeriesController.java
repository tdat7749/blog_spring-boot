package com.example.blog_springboot.modules.series.controller;

import com.example.blog_springboot.commons.Constants;
import com.example.blog_springboot.commons.PagingResponse;
import com.example.blog_springboot.commons.SuccessResponse;
import com.example.blog_springboot.modules.series.dto.CreateSeriesDTO;
import com.example.blog_springboot.modules.series.dto.UpdateSeriesDTO;
import com.example.blog_springboot.modules.series.model.Series;
import com.example.blog_springboot.modules.series.service.SeriesService;
import com.example.blog_springboot.modules.series.viewmodel.SeriesListPostVm;
import com.example.blog_springboot.modules.series.viewmodel.SeriesVm;
import com.example.blog_springboot.modules.user.model.User;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping(value = "/api/series")
public class SeriesController {
    private final SeriesService seriesService;

    public SeriesController(SeriesService seriesService){
        this.seriesService = seriesService;
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<SuccessResponse<SeriesVm>> getSeriesById(@PathVariable int id){
        var result = seriesService.getSeriesById(id);

        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    @GetMapping("/slug/{slug}")
    @ResponseBody
    public ResponseEntity<SuccessResponse<SeriesListPostVm>> getSeriesDetail(@PathVariable String slug){
        var result = seriesService.getSeriesDetail(slug);

        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    @GetMapping("/")
    @ResponseBody
    public ResponseEntity<SuccessResponse<PagingResponse<List<SeriesVm>>>> getAllSeries(
            @RequestParam(name = "keyword",required = false,defaultValue = "") String keyword,
            @RequestParam(name = "pageIndex",required = true,defaultValue = "0") Integer pageIndex,
            @RequestParam(name = "sortBy",required = false,defaultValue = Constants.SORT_BY_CREATED_AT) String sortBy
    ){
        var result = seriesService.getAllSeries(keyword,sortBy,pageIndex);

        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    @GetMapping("/user")
    @ResponseBody
    public ResponseEntity<SuccessResponse<List<SeriesVm>>> getListSeriesByUserPrincipal(
            @AuthenticationPrincipal User userPrincipal
    ){
        var result = seriesService.getListSeriesByUserPrincipal(userPrincipal);

        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    @PostMapping("")
    @ResponseBody
    public ResponseEntity<SuccessResponse<SeriesVm>> createSeries(@RequestBody @Valid CreateSeriesDTO dto,@AuthenticationPrincipal User userPrincipal){
        var result = seriesService.createSeries(dto,userPrincipal);

        return new ResponseEntity<>(result,HttpStatus.CREATED);
    }

    @PutMapping("/{seriesId}")
    @ResponseBody
    public ResponseEntity<SuccessResponse<SeriesVm>> updateSeries(@RequestBody @Valid UpdateSeriesDTO dto,@PathVariable("seriesId") int id,@AuthenticationPrincipal User userPrincipal){
        var result = seriesService.updateSeries(dto,id,userPrincipal);

        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity<SuccessResponse<Boolean>> deleteSeries(@PathVariable int id, @AuthenticationPrincipal User userPrincipal){
        var result = seriesService.deleteSeries(id,userPrincipal);

        return new ResponseEntity<>(result,HttpStatus.OK);
    }
}
