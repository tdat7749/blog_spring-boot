package com.example.blog_springboot.modules.authenticate.service;

import com.example.blog_springboot.commons.SuccessResponse;
import com.example.blog_springboot.modules.authenticate.dto.LoginDTO;
import com.example.blog_springboot.modules.authenticate.dto.RegisterDTO;
import com.example.blog_springboot.modules.authenticate.dto.VerifyDTO;
import com.example.blog_springboot.modules.authenticate.viewmodel.AuthenVm;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {
    public SuccessResponse<AuthenVm> login(LoginDTO dto);

    public SuccessResponse<Boolean> register(RegisterDTO dto);

    public SuccessResponse<Boolean> verifyAccount(VerifyDTO dto);

    public SuccessResponse<Boolean> resendEmail(String email);

    public SuccessResponse<Boolean> lockAccount(int id);

    public SuccessResponse<Boolean> unLockAccount(int id);
}
