package com.example.blog_springboot.modules.user.service;

import com.example.blog_springboot.commons.PagingResponse;
import com.example.blog_springboot.commons.SuccessResponse;
import com.example.blog_springboot.modules.user.dto.ChangeInformationDTO;
import com.example.blog_springboot.modules.user.dto.ChangePasswordDTO;
import com.example.blog_springboot.modules.user.model.User;
import com.example.blog_springboot.modules.user.viewmodel.UserVm;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    public SuccessResponse<Boolean> changePassword(ChangePasswordDTO dto,User userPrincipal);
    public SuccessResponse<Boolean> changeAvatar(String avatar,User userPrincipal);

    public SuccessResponse<UserVm> changeInformation(ChangeInformationDTO dto,User userPrincipal);

    public SuccessResponse<PagingResponse<List<UserVm>>> getListFollowing(String sortBy,int pageIndex,int userId);

    public SuccessResponse<PagingResponse<List<UserVm>>> getListFollowers(String sortBy,int pageIndex,int userId);


}
