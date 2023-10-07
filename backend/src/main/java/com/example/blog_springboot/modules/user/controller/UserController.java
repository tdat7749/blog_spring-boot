package com.example.blog_springboot.modules.user.controller;

import com.example.blog_springboot.commons.Constants;
import com.example.blog_springboot.commons.PagingResponse;
import com.example.blog_springboot.commons.SuccessResponse;
import com.example.blog_springboot.modules.notification.service.UserNotificationService;
import com.example.blog_springboot.modules.notification.viewmodel.NotificationVm;
import com.example.blog_springboot.modules.user.dto.ChangeInformationDTO;
import com.example.blog_springboot.modules.user.dto.ChangePasswordDTO;
import com.example.blog_springboot.modules.user.dto.ChangePermissionDTO;
import com.example.blog_springboot.modules.user.dto.ForgotPasswordDTO;
import com.example.blog_springboot.modules.user.model.User;
import com.example.blog_springboot.modules.user.service.UserService;
import com.example.blog_springboot.modules.user.viewmodel.UserDetailVm;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    private final UserNotificationService userNotificationService;

    public UserController(UserService userService, UserNotificationService userNotificationService){
        this.userService = userService;
        this.userNotificationService = userNotificationService;
    }

    @PatchMapping("/password")
    @ResponseBody
    public ResponseEntity<SuccessResponse<Boolean>> changePassword(@RequestBody @Valid ChangePasswordDTO dto, @AuthenticationPrincipal User userPrincipal){
        var result = userService.changePassword(dto,userPrincipal);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PatchMapping("/information")
    @ResponseBody
    public ResponseEntity<SuccessResponse<UserDetailVm>> changeInformation(@RequestBody @Valid ChangeInformationDTO dto, @AuthenticationPrincipal User userPrincipal){
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
    public ResponseEntity<SuccessResponse<PagingResponse<List<UserDetailVm>>>> getListFollowing(
            @PathVariable("userId") int userId,
            @RequestParam(value = "pageIndex",defaultValue = "0") int pageIndex,
            @RequestParam(value = "sortBy",required = false,defaultValue = Constants.SORT_BY_CREATED_AT) String sortBy
            ){
        var result = userService.getListFollowing(sortBy,pageIndex,userId);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/{userId}/follower")
    @ResponseBody
    public ResponseEntity<SuccessResponse<PagingResponse<List<UserDetailVm>>>> getListFollowers(
            @PathVariable("userId") int userId,
            @RequestParam(value = "pageIndex",defaultValue = "0") int pageIndex,
            @RequestParam(value = "sortBy",required = false,defaultValue = Constants.SORT_BY_CREATED_AT) String sortBy
    ){
        var result = userService.getListFollowers(sortBy,pageIndex,userId);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/{email}/forgot-mail")
    @ResponseBody
    public ResponseEntity<SuccessResponse<Boolean>> sendCodeForgotPassword(
            @PathVariable("email") String email
           ){
        var result = userService.sendCodeForgotPassword(email);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PatchMapping("/permission")
    @ResponseBody
    public ResponseEntity<SuccessResponse<Boolean>> changePermission(
            @RequestBody ChangePermissionDTO dto
    ){
        var result = userService.changePermission(dto);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PatchMapping("/forgot")
    @ResponseBody
    public ResponseEntity<SuccessResponse<Boolean>> forgotPassword(
            @RequestBody ForgotPasswordDTO dto
            ){
        var result = userService.forgotPassword(dto);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/me")
    @ResponseBody
    public ResponseEntity<SuccessResponse<UserDetailVm>> getMe(@AuthenticationPrincipal User userPrincipal){
        var result = userService.getMe(userPrincipal);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/top10-noti")
    @ResponseBody
    public ResponseEntity<SuccessResponse<List<NotificationVm>>> getTop10NotificationCurrentUser(@AuthenticationPrincipal User userPrincipal){
        var result = userNotificationService.getTop10NotificationCurrentUser(userPrincipal);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/notifications")
    @ResponseBody
    public ResponseEntity<SuccessResponse<PagingResponse<List<NotificationVm>>>> getTop10NotificationCurrentUser(
            @RequestParam(value = "pageIndex", defaultValue = "0",required = true) int pageIndex,
            @AuthenticationPrincipal User userPrincipal
    ){
        var result = userNotificationService.getNotificationCurrentUser(pageIndex,userPrincipal);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PatchMapping("/{notiId}/notifications")
    @ResponseBody
    public ResponseEntity<SuccessResponse<Boolean>> readNotification(
            @PathVariable("notiId") int notificationId,
            @AuthenticationPrincipal User userPrincipal
    ){
        var result = userNotificationService.readNotification(notificationId,userPrincipal);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PatchMapping("/notifications")
    @ResponseBody
    public ResponseEntity<SuccessResponse<Boolean>> readAllNotification(
            @AuthenticationPrincipal User userPrincipal
    ){
        var result = userNotificationService.readAllNotification(userPrincipal);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
