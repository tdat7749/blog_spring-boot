package com.example.blog_springboot.modules.authenticate.controller;

import com.example.blog_springboot.commons.SuccessResponse;
import com.example.blog_springboot.modules.authenticate.dto.LoginDTO;
import com.example.blog_springboot.modules.authenticate.dto.RegisterDTO;
import com.example.blog_springboot.modules.authenticate.dto.ResendMailDTO;
import com.example.blog_springboot.modules.authenticate.dto.VerifyDTO;
import com.example.blog_springboot.modules.authenticate.service.AuthService;
import com.example.blog_springboot.modules.authenticate.viewmodel.AuthenVm;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/auth")
public class AuthenController {
    final private AuthService authService;
    public AuthenController(AuthService authService){
        this.authService = authService;
    }

    @PostMapping(value = "/login")
    @ResponseBody
    public ResponseEntity<SuccessResponse<AuthenVm>> login(@RequestBody @Valid LoginDTO dto){
        var result = authService.login(dto);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping(value = "/register")
    @ResponseBody
    public ResponseEntity<SuccessResponse<Boolean>> register(@RequestBody @Valid RegisterDTO dto){
        var result = authService.register(dto);

        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    @PostMapping(value = "/refresh")
    @ResponseBody
    public ResponseEntity<SuccessResponse<String>> refreshToken(@RequestHeader("Rftoken") String refreshToken){
        var result = authService.refreshToken(refreshToken);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping(value = "/logout")
    @ResponseBody
    public ResponseEntity<SuccessResponse<Boolean>> logout(){
        SecurityContextHolder.clearContext();

        return new ResponseEntity<>(new SuccessResponse<Boolean>("Thành công",true),HttpStatus.OK);
    }

    @PostMapping(value = "/verify")
    @ResponseBody
    public ResponseEntity<SuccessResponse<Boolean>> verifyAccount(@RequestBody @Valid VerifyDTO dto){
        var result = authService.verifyAccount(dto);

        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    @PostMapping(value = "/resend")
    @ResponseBody
    public ResponseEntity<SuccessResponse<Boolean>> resendEmail(@RequestBody @Valid ResendMailDTO dto){
        var result = authService.resendEmail(dto);

        return new ResponseEntity<>(result,HttpStatus.OK);
    }
}
