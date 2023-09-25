package com.example.blog_springboot.modules.post.service;

import com.example.blog_springboot.commons.PagingResponse;
import com.example.blog_springboot.commons.SuccessResponse;
import com.example.blog_springboot.modules.post.dto.CreatePostDTO;
import com.example.blog_springboot.modules.post.dto.UpdatePostDTO;
import com.example.blog_springboot.modules.post.dto.UpdatePostStatusDTO;
import com.example.blog_springboot.modules.post.viewmodel.PostListVm;
import com.example.blog_springboot.modules.post.viewmodel.PostVm;
import com.example.blog_springboot.modules.user.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PostService {

    public SuccessResponse<PostListVm> createPost(CreatePostDTO dto, User userPrincipal);
    public SuccessResponse<PostListVm> updatePost(UpdatePostDTO dto,int id, User userPrincipal);

    public SuccessResponse<Boolean> deletePost(int id,User userPrincipal);
    public SuccessResponse<Boolean> updateStatus(int id, User userPrincipal, UpdatePostStatusDTO dto);

    public SuccessResponse<Boolean> addPostToSeries(int postId,int seriesId,User userPrincipal);

    // for client
    public SuccessResponse<PagingResponse<List<PostListVm>>> getAllPostByTag(String tagSlug,String sortBy,int pageIndex);
    public SuccessResponse<PagingResponse<List<PostListVm>>> getAllPost(String sortBy, int pageIndex);

    public SuccessResponse<PostVm> getPostBySlug(String slug);

    public SuccessResponse<PagingResponse<List<PostListVm>>> getAllPostAuthor(String username,String sortBy,int pageIndex);
}
