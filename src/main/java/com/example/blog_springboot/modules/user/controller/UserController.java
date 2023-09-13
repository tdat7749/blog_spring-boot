package com.example.blog_springboot.modules.user.controller;

import com.example.blog_springboot.commons.Constants;
import com.example.blog_springboot.commons.PagingResponse;
import com.example.blog_springboot.commons.SuccessResponse;
import com.example.blog_springboot.modules.user.dto.ChangeInformationDTO;
import com.example.blog_springboot.modules.user.dto.ChangePasswordDTO;
import com.example.blog_springboot.modules.user.model.User;
import com.example.blog_springboot.modules.user.service.UserService;
import com.example.blog_springboot.modules.user.viewmodel.UserVm;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/{userId}/following")
    @ResponseBody
    public ResponseEntity<SuccessResponse<PagingResponse<List<UserVm>>>> getListFollowing(
            @PathVariable("userId") int userId,
            @RequestParam(value = "pageIndex",defaultValue = "0") int pageIndex,
            @RequestParam(value = "sortBy",required = false,defaultValue = Constants.SORT_BY_CREATED_AT) String sortBy
            ){
        var result = userService.getListFollowing(sortBy,pageIndex,userId);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/{userId}/follower")
    @ResponseBody
    public ResponseEntity<SuccessResponse<PagingResponse<List<UserVm>>>> getListFollowers(
            @PathVariable("userId") int userId,
            @RequestParam(value = "pageIndex",defaultValue = "0") int pageIndex,
            @RequestParam(value = "sortBy",required = false,defaultValue = Constants.SORT_BY_CREATED_AT) String sortBy
    ){
        var result = userService.getListFollowers(sortBy,pageIndex,userId);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
