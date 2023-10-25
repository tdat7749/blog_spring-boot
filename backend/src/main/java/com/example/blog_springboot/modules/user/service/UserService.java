package com.example.blog_springboot.modules.user.service;

import com.example.blog_springboot.commons.PagingResponse;
import com.example.blog_springboot.commons.SuccessResponse;
import com.example.blog_springboot.modules.user.dto.ChangeInformationDTO;
import com.example.blog_springboot.modules.user.dto.ChangePasswordDTO;
import com.example.blog_springboot.modules.user.dto.ChangePermissionDTO;
import com.example.blog_springboot.modules.user.dto.ForgotPasswordDTO;
import com.example.blog_springboot.modules.user.model.User;
import com.example.blog_springboot.modules.user.viewmodel.UserDetailVm;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    public SuccessResponse<Boolean> changePassword(ChangePasswordDTO dto,User userPrincipal);
    public SuccessResponse<String> changeAvatar(String avatar,User userPrincipal);

    public SuccessResponse<UserDetailVm> changeInformation(ChangeInformationDTO dto, User userPrincipal);

    public SuccessResponse<UserDetailVm> getMe(User user);

    public SuccessResponse<Boolean> changePermission(ChangePermissionDTO dto);

    public SuccessResponse<Boolean> forgotPassword(ForgotPasswordDTO dto);

    public SuccessResponse<Boolean> sendCodeForgotPassword(String email);

    public SuccessResponse<PagingResponse<List<UserDetailVm>>> getListFollowing(String sortBy, int pageIndex, String userName);

    public SuccessResponse<PagingResponse<List<UserDetailVm>>> getListFollowers(String sortBy, int pageIndex, String userName);

    public SuccessResponse<UserDetailVm> getAuthor(String userName);


}
