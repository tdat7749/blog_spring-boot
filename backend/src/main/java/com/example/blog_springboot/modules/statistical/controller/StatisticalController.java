package com.example.blog_springboot.modules.statistical.controller;


import com.example.blog_springboot.commons.SuccessResponse;
import com.example.blog_springboot.modules.statistical.service.StatisticalService;
import com.example.blog_springboot.modules.statistical.viewmodel.DashboardVm;
import org.apache.http.protocol.HTTP;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping(value = "/api/statistical")
public class StatisticalController {
    private final StatisticalService statisticalService;
    public StatisticalController(StatisticalService statisticalService){
        this.statisticalService = statisticalService;
    }

    @GetMapping("/dashboard")
    @ResponseBody
    public ResponseEntity<SuccessResponse<DashboardVm>> getDashboard(){
        var result = statisticalService.getDashboard();

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
