package com.example.blog_springboot.modules.follow.controller;

import com.example.blog_springboot.commons.SuccessResponse;
import com.example.blog_springboot.modules.follow.service.FollowService;
import com.example.blog_springboot.modules.user.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/follows")
public class FollowController {
    private final FollowService followService;
    public FollowController(FollowService followService){
        this.followService = followService;
    }

    @GetMapping("/{userName}")
    public ResponseEntity<SuccessResponse<Boolean>> checkFollowed(@PathVariable("userName") @NotNull String userName, @AuthenticationPrincipal User userPrincipal) throws JsonProcessingException {
        var result = followService.checkFollowed(userName,userPrincipal);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @PostMapping("/{id}")
    public ResponseEntity<SuccessResponse<Boolean>> follow(@PathVariable("id") @NotNull int id, @AuthenticationPrincipal User userPrincipal) throws JsonProcessingException {
        var result = followService.follow(id,userPrincipal);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SuccessResponse<Boolean>> unfollow(@PathVariable("id") @NotNull int id, @AuthenticationPrincipal User userPrincipal){
        var result = followService.unFollow(id,userPrincipal);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
