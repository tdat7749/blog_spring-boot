package com.example.blog_springboot.modules.comment.controller;


import com.example.blog_springboot.commons.Constants;
import com.example.blog_springboot.commons.PagingResponse;
import com.example.blog_springboot.commons.SuccessResponse;
import com.example.blog_springboot.modules.comment.dto.CreateCommentDTO;
import com.example.blog_springboot.modules.comment.dto.EditCommentDTO;
import com.example.blog_springboot.modules.comment.service.CommentService;
import com.example.blog_springboot.modules.comment.viewmodel.CommentVm;
import com.example.blog_springboot.modules.user.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.yaml.snakeyaml.scanner.Constant;

import java.util.List;

@RestController()
@RequestMapping(value = "/api/comments")
public class CommentController {
    private final CommentService commentService;
    public CommentController(CommentService commentService){
        this.commentService = commentService;
    }

//    private static final Logger logger = LoggerFactory.getLogger(CommentController.class);
//    logger.info("Logger comment");


    @GetMapping("/{slug}/posts")
    @ResponseBody
    public ResponseEntity<SuccessResponse<PagingResponse<List<CommentVm>>>> getListCommentPost(
            @PathVariable("slug") String slug,
            @RequestParam(value = "sortBy",defaultValue = Constants.SORT_BY_CREATED_AT, required = false) String sortBy,
            @RequestParam(value = "pageIndex",defaultValue = "0",required = true) int pageIndex
    ){
        var result = commentService.getListCommentPost(slug,sortBy,pageIndex);

        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    @PostMapping("/{postId}/posts")
    @ResponseBody
    public ResponseEntity<SuccessResponse<CommentVm>> createComment(
            @PathVariable("postId") int postId,
            @RequestBody CreateCommentDTO dto,
            @AuthenticationPrincipal User userPrincipal
    ) throws JsonProcessingException {
        var result = commentService.createComment(dto,postId,userPrincipal);

        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    @PatchMapping("/")
    @ResponseBody
    public ResponseEntity<SuccessResponse<CommentVm>> editComment(
            @RequestBody EditCommentDTO dto,
            @AuthenticationPrincipal User userPrincipal
    ){
        var result = commentService.editComment(dto,userPrincipal);

        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity<SuccessResponse<Boolean>> editComment(
            @PathVariable("id") int commentId,
            @AuthenticationPrincipal User userPrincipal
    ){
        var result = commentService.deleteComment(commentId,userPrincipal);

        return new ResponseEntity<>(result,HttpStatus.OK);
    }
}
