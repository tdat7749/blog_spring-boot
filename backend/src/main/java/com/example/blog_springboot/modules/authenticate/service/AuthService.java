package com.example.blog_springboot.modules.authenticate.service;

import com.example.blog_springboot.commons.SuccessResponse;
import com.example.blog_springboot.modules.authenticate.dto.LoginDTO;
import com.example.blog_springboot.modules.authenticate.dto.RegisterDTO;
import com.example.blog_springboot.modules.authenticate.dto.ResendMailDTO;
import com.example.blog_springboot.modules.authenticate.dto.VerifyDTO;
import com.example.blog_springboot.modules.authenticate.viewmodel.AuthenVm;
import com.example.blog_springboot.modules.user.model.User;

import org.springframework.stereotype.Service;

@Service
public interface AuthService {
    public SuccessResponse<AuthenVm> login(LoginDTO dto);

    public SuccessResponse<Boolean> register(RegisterDTO dto);

    public SuccessResponse<Boolean> verifyAccount(VerifyDTO dto);

    public SuccessResponse<Boolean> resendEmail(ResendMailDTO dto);

    public SuccessResponse<String> refreshToken(String refreshToken);

    public SuccessResponse<Boolean> lockAccount(int id, User user);

    public SuccessResponse<Boolean> unLockAccount(int id);
}
