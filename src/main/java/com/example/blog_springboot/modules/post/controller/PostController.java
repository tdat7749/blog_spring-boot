package com.example.blog_springboot.modules.post.controller;

import com.example.blog_springboot.commons.Constants;
import com.example.blog_springboot.commons.PagingResponse;
import com.example.blog_springboot.commons.SuccessResponse;
import com.example.blog_springboot.modules.post.dto.CreatePostDTO;
import com.example.blog_springboot.modules.post.dto.UpdatePostDTO;
import com.example.blog_springboot.modules.post.dto.UpdatePostStatusDTO;
import com.example.blog_springboot.modules.post.service.PostService;
import com.example.blog_springboot.modules.post.viewmodel.PostListVm;
import com.example.blog_springboot.modules.post.viewmodel.PostVm;
import com.example.blog_springboot.modules.user.model.User;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    private final PostService postService;
    public PostController(PostService postService){
        this.postService = postService;
    }

    @PostMapping("/")
    @ResponseBody
    public ResponseEntity<SuccessResponse<PostListVm>> createPost(@RequestBody @Valid CreatePostDTO dto, @AuthenticationPrincipal User userPrincipal){
        var result = postService.createPost(dto,userPrincipal);

        return new ResponseEntity<>(result,HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @ResponseBody
    public ResponseEntity<SuccessResponse<PostListVm>> updatePost(@PathVariable("id") int id,@RequestBody @Valid UpdatePostDTO dto, @AuthenticationPrincipal User userPrincipal){
        var result = postService.updatePost(dto,id,userPrincipal);

        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity<SuccessResponse<Boolean>> deletePost(@PathVariable("id") int id, @AuthenticationPrincipal User userPrincipal){
        var result = postService.deletePost(id,userPrincipal);

        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    @ResponseBody
    public ResponseEntity<SuccessResponse<Boolean>> updateStatus(@PathVariable("id") int id, @RequestBody UpdatePostStatusDTO dto, @AuthenticationPrincipal User userPrincipal){
        var result = postService.updateStatus(id,userPrincipal,dto);

        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    @GetMapping("/{slug}/tag")
    @ResponseBody
    public ResponseEntity<SuccessResponse<PagingResponse<List<PostListVm>>>> getAllPostByTag(
            @PathVariable("slug") String tagSlug,
            @RequestParam(name = "pageIndex",required = true,defaultValue = "0") Integer pageIndex,
            @RequestParam(name = "sortBy",required = false,defaultValue = Constants.SORT_BY_CREATED_AT) String sortBy)
    {
        var result = postService.getAllPostByTag(tagSlug,sortBy,pageIndex);

        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    @GetMapping("/")
    @ResponseBody
    public ResponseEntity<SuccessResponse<PagingResponse<List<PostListVm>>>> getAllPost(
            @RequestParam(name = "pageIndex",required = true,defaultValue = "0") Integer pageIndex,
            @RequestParam(name = "sortBy",required = false,defaultValue = Constants.SORT_BY_CREATED_AT) String sortBy)
    {
        var result = postService.getAllPost(sortBy,pageIndex);

        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    @GetMapping("/slug/{slug}")
    @ResponseBody
    public ResponseEntity<SuccessResponse<PostVm>> getPostBySlug(@PathVariable("slug") String slug){
        var result = postService.getPostBySlug(slug);

        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    @PostMapping("/{postId}/series/{seriesId}")
    @ResponseBody
    public ResponseEntity<SuccessResponse<Boolean>> getPostBySlug(@PathVariable("postId") int postId,@PathVariable("seriesId") int seriesId,@AuthenticationPrincipal User userPrincipal){
        var result = postService.addPostToSeries(postId,seriesId,userPrincipal);

        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    @GetMapping("/{userName}")
    @ResponseBody
    public ResponseEntity<SuccessResponse<PagingResponse<List<PostListVm>>>> getAllPostAuthor(
            @PathVariable("userName") String userName,
            @RequestParam(name = "pageIndex",required = true,defaultValue = "0") Integer pageIndex,
            @RequestParam(name = "sortBy",required = false,defaultValue = Constants.SORT_BY_CREATED_AT) String sortBy
    )
    {
        var result = postService.getAllPostAuthor(userName,sortBy,pageIndex);

        return new ResponseEntity<>(result,HttpStatus.OK);
    }
}
