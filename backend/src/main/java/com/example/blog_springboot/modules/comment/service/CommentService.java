package com.example.blog_springboot.modules.comment.service;


import com.example.blog_springboot.commons.PagingResponse;
import com.example.blog_springboot.commons.SuccessResponse;
import com.example.blog_springboot.modules.comment.dto.CreateCommentDTO;
import com.example.blog_springboot.modules.comment.dto.EditCommentDTO;
import com.example.blog_springboot.modules.comment.viewmodel.CommentVm;
import com.example.blog_springboot.modules.user.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CommentService {
    public SuccessResponse<CommentVm> createComment(CreateCommentDTO dto,int postId, User user) throws JsonProcessingException;
    public SuccessResponse<CommentVm> editComment(EditCommentDTO dto,User user);

    public SuccessResponse<Boolean> deleteComment(int id,User user);
    public SuccessResponse<PagingResponse<List<CommentVm>>> getListCommentPost(String slug,String sortBy,int pageIndex);

    public long countCommentPost(int postId);
}
