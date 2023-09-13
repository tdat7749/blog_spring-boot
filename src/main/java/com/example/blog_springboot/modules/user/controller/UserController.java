package com.example.blog_springboot.modules.user.controller;

import com.example.blog_springboot.commons.SuccessResponse;
import com.example.blog_springboot.modules.user.dto.ChangeInformationDTO;
import com.example.blog_springboot.modules.user.dto.ChangePasswordDTO;
import com.example.blog_springboot.modules.user.model.User;
import com.example.blog_springboot.modules.user.service.UserService;
import com.example.blog_springboot.modules.user.viewmodel.UserVm;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PatchMapping("/password")
    @ResponseBody
    public ResponseEntity<SuccessResponse<Boolean>> changePassword(@RequestBody @Valid ChangePasswordDTO dto, @AuthenticationPrincipal User userPrincipal){
        var result = userService.changePassword(dto,userPrincipal);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PatchMapping("/information")
    @ResponseBody
    public ResponseEntity<SuccessResponse<UserVm>> changeInformation(@RequestBody @Valid ChangeInformationDTO dto, @AuthenticationPrincipal User userPrincipal){
        var result = userService.changeInformation(dto,userPrincipal);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PatchMapping("/avatar")
    @ResponseBody
    public ResponseEntity<SuccessResponse<Boolean>> changeAvatar(@RequestBody String avatar, @AuthenticationPrincipal User userPrincipal){
        var result = userService.changeAvatar(avatar,userPrincipal);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
