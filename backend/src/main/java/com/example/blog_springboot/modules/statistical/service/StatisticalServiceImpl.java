package com.example.blog_springboot.modules.statistical.service;

import com.example.blog_springboot.commons.SuccessResponse;
import com.example.blog_springboot.modules.post.repository.PostRepository;
import com.example.blog_springboot.modules.series.repository.SeriesRepository;
import com.example.blog_springboot.modules.statistical.viewmodel.DashboardVm;
import com.example.blog_springboot.modules.tag.repository.TagRepository;
import com.example.blog_springboot.modules.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class StatisticalServiceImpl implements StatisticalService {
    private final PostRepository postRepository;
    private final TagRepository tagRepository;
    private final SeriesRepository seriesRepository;
    private final UserRepository userRepository;

    public StatisticalServiceImpl(
            PostRepository postRepository,
            TagRepository tagRepository,
            SeriesRepository seriesRepository,
            UserRepository userRepository
    ){
        this.postRepository = postRepository;
        this.tagRepository = tagRepository;
        this.seriesRepository = seriesRepository;
        this.userRepository = userRepository;
    }

    @Override
    public SuccessResponse<DashboardVm> getDashboard() {
        int totalAdmin = userRepository.getTotalAdmin();
        int totalUser = userRepository.getTotalUser();
        int totalSeries = seriesRepository.getTotalSeries();
        int totalTag = tagRepository.getTotalTag();
        int totalPostNoPublished = postRepository.getTotalPostNoPublished();
        int totalPostPublished = postRepository.getTotalPostPublished();

        var dashboardVm = new DashboardVm();
        dashboardVm.setTotalAdmin(totalAdmin);
        dashboardVm.setTotalUser(totalUser);
        dashboardVm.setTotalTag(totalTag);
        dashboardVm.setTotalSeries(totalSeries);
        dashboardVm.setTotalPostNoPublished(totalPostNoPublished);
        dashboardVm.setTotalPostPublished(totalPostPublished);

        return new SuccessResponse<>("Thành công",dashboardVm);
    }
}
