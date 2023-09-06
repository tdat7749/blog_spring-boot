package com.example.blog_springboot.modules.series.controller;

import com.example.blog_springboot.commons.Contants;
import com.example.blog_springboot.commons.PagingRequestDTO;
import com.example.blog_springboot.commons.PagingResponse;
import com.example.blog_springboot.commons.SuccessResponse;
import com.example.blog_springboot.modules.series.dto.CreateSeriesDTO;
import com.example.blog_springboot.modules.series.dto.UpdateSeriesDTO;
import com.example.blog_springboot.modules.series.model.Series;
import com.example.blog_springboot.modules.series.service.SeriesService;
import com.example.blog_springboot.modules.series.viewmodel.SeriesVm;
import com.example.blog_springboot.modules.user.model.User;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
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
    public ResponseEntity<SuccessResponse<Series>> getListPostBySeries(@PathVariable String slug){
        var result = seriesService.getListPostBySeries(slug);

        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    @GetMapping("/")
    @ResponseBody
    public ResponseEntity<SuccessResponse<PagingResponse<List<SeriesVm>>>> getAllSeries(
            @RequestParam(name = "pageIndex",required = true,defaultValue = "0") Integer pageIndex,
            @RequestParam(name = "sortBy",required = false,defaultValue = Contants.SORT_BY_CREATED_AT) String sortBy
    ){
        PagingRequestDTO dto = new PagingRequestDTO();
        dto.setPageIndex(pageIndex);
        dto.setSortBy(sortBy);
        var result = seriesService.getAllSeries(dto);

        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    @PostMapping("")
    @ResponseBody
    public ResponseEntity<SuccessResponse<SeriesVm>> createSeries(@RequestBody @Valid CreateSeriesDTO dto){
        var result = seriesService.createSeries(dto);

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
