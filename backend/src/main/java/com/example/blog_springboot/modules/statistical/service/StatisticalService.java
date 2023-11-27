package com.example.blog_springboot.modules.statistical.service;

import com.example.blog_springboot.commons.SuccessResponse;
import com.example.blog_springboot.modules.statistical.viewmodel.DashboardVm;
import org.springframework.stereotype.Service;

@Service
public interface StatisticalService {
    public SuccessResponse<DashboardVm> getDashboard();
}
