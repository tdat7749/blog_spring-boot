package com.example.blog_springboot.modules.post.controller;

import com.example.blog_springboot.commons.Constants;
import com.example.blog_springboot.commons.PagingResponse;
import com.example.blog_springboot.commons.SuccessResponse;
import com.example.blog_springboot.modules.likepost.service.LikePostService;
import com.example.blog_springboot.modules.post.dto.CreatePostDTO;
import com.example.blog_springboot.modules.post.dto.UpdatePostDTO;
import com.example.blog_springboot.modules.post.dto.UpdatePostStatusDTO;
import com.example.blog_springboot.modules.post.service.PostService;
import com.example.blog_springboot.modules.post.viewmodel.PostListVm;
import com.example.blog_springboot.modules.post.viewmodel.PostVm;
import com.example.blog_springboot.modules.user.model.User;
import com.example.blog_springboot.modules.user.viewmodel.UserDetailVm;
import com.fasterxml.jackson.core.JsonProcessingException;
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
    private final LikePostService likePostService;

    public PostController(PostService postService, LikePostService likePostService) {
        this.postService = postService;
        this.likePostService = likePostService;
    }

    @PostMapping("/")
    @ResponseBody
    public ResponseEntity<SuccessResponse<PostListVm>> createPost(@RequestBody @Valid CreatePostDTO dto,
            @AuthenticationPrincipal User userPrincipal) throws JsonProcessingException {
        var result = postService.createPost(dto, userPrincipal);

        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @ResponseBody
    public ResponseEntity<SuccessResponse<PostVm>> updatePost(@PathVariable("id") int id,
            @RequestBody @Valid UpdatePostDTO dto, @AuthenticationPrincipal User userPrincipal) {
        var result = postService.updatePost(dto, id, userPrincipal);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity<SuccessResponse<Boolean>> deletePost(@PathVariable("id") int id,
            @AuthenticationPrincipal User userPrincipal) {
        var result = postService.deletePost(id, userPrincipal);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    @ResponseBody
    public ResponseEntity<SuccessResponse<Boolean>> updateStatus(@PathVariable("id") int id,
            @RequestBody UpdatePostStatusDTO dto, @AuthenticationPrincipal User userPrincipal)
            throws JsonProcessingException {
        var result = postService.updateStatus(id, userPrincipal, dto);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PatchMapping("/{slug}/view")
    @ResponseBody
    public ResponseEntity<SuccessResponse<Boolean>> plusView(@PathVariable("slug") String slug) {
        var result = postService.plusView(slug);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/{slug}/tags")
    @ResponseBody
    public ResponseEntity<SuccessResponse<PagingResponse<List<PostListVm>>>> getAllPostByTag(
            @PathVariable("slug") String tagSlug,
            @RequestParam(name = "keyword", required = false, defaultValue = "") String keyword,
            @RequestParam(name = "pageIndex", required = true, defaultValue = "0") Integer pageIndex,
            @RequestParam(name = "sortBy", required = false, defaultValue = Constants.SORT_BY_CREATED_AT) String sortBy) {
        var result = postService.getAllPostByTag(tagSlug, keyword, sortBy, pageIndex);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/latest")
    @ResponseBody
    public ResponseEntity<SuccessResponse<List<PostListVm>>> getLatestPost() {
        var result = postService.getLatestPost();

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/most-view")
    @ResponseBody
    public ResponseEntity<SuccessResponse<List<PostListVm>>> getPostMostView() {
        var result = postService.getPostMostView();

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/")
    @ResponseBody
    public ResponseEntity<SuccessResponse<PagingResponse<List<PostListVm>>>> getAllPost(
            @RequestParam(name = "keyword", required = false, defaultValue = "") String keyword,
            @RequestParam(name = "pageIndex", required = true, defaultValue = "0") Integer pageIndex,
            @RequestParam(name = "sortBy", required = false, defaultValue = Constants.SORT_BY_CREATED_AT) String sortBy) {
        var result = postService.getAllPost(keyword, sortBy, pageIndex);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/user")
    @ResponseBody
    public ResponseEntity<SuccessResponse<PagingResponse<List<PostListVm>>>> getAllPostByCurrentUser(
            @RequestParam(name = "keyword", required = false, defaultValue = "") String keyword,
            @RequestParam(name = "pageIndex", required = true, defaultValue = "0") Integer pageIndex,
            @RequestParam(name = "sortBy", required = false, defaultValue = Constants.SORT_BY_CREATED_AT) String sortBy,
            @AuthenticationPrincipal User userPrincipal) {
        var result = postService.getAllByCurrentUser(userPrincipal, keyword, sortBy, pageIndex);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/not-published")
    @ResponseBody
    public ResponseEntity<SuccessResponse<PagingResponse<List<PostListVm>>>> getAllPostNotPublished(
            @RequestParam(name = "keyword", required = false, defaultValue = "") String keyword,
            @RequestParam(name = "pageIndex", required = true, defaultValue = "0") Integer pageIndex,
            @RequestParam(name = "sortBy", required = false, defaultValue = Constants.SORT_BY_CREATED_AT) String sortBy) {
        var result = postService.getAllPostNotPublished(sortBy, keyword, pageIndex);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/slug/{slug}")
    @ResponseBody
    public ResponseEntity<SuccessResponse<PostVm>> getPostBySlug(@PathVariable("slug") String slug) {
        var result = postService.getPostBySlug(slug);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/not-belong-series/user")
    @ResponseBody
    public ResponseEntity<SuccessResponse<List<PostListVm>>> getAllPostNotBeLongSeries(
            @AuthenticationPrincipal User userPrincipal) {
        var result = postService.getAllPostNotBeLongSeries(userPrincipal);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/slug/{slug}/user")
    @ResponseBody
    public ResponseEntity<SuccessResponse<PostVm>> getPostBySlug(@PathVariable("slug") String slug,
            @AuthenticationPrincipal User userPrincipal) {
        var result = postService.getPostBySlug(slug, userPrincipal);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/{postId}/series/{seriesId}")
    @ResponseBody
    public ResponseEntity<SuccessResponse<Boolean>> addPostToSeries(@PathVariable("postId") int postId,
            @PathVariable("seriesId") int seriesId, @AuthenticationPrincipal User userPrincipal)
            throws JsonProcessingException {
        var result = postService.addPostToSeries(postId, seriesId, userPrincipal);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PatchMapping("/{postId}/series/{seriesId}")
    @ResponseBody
    public ResponseEntity<SuccessResponse<Boolean>> removePostFromSeries(@PathVariable("postId") int postId,
            @PathVariable("seriesId") int seriesId, @AuthenticationPrincipal User userPrincipal) {
        var result = postService.removePostFromSeries(postId, seriesId, userPrincipal);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/{userName}")
    @ResponseBody
    public ResponseEntity<SuccessResponse<PagingResponse<List<PostListVm>>>> getAllPostAuthor(
            @RequestParam(name = "keyword", required = false, defaultValue = "") String keyword,
            @PathVariable("userName") String userName,
            @RequestParam(name = "pageIndex", required = true, defaultValue = "0") Integer pageIndex,
            @RequestParam(name = "sortBy", required = false, defaultValue = Constants.SORT_BY_CREATED_AT) String sortBy) {
        var result = postService.getAllPostAuthor(userName, keyword, sortBy, pageIndex);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/{postId}/likes")
    @ResponseBody
    public ResponseEntity<SuccessResponse<Boolean>> likePost(@PathVariable("postId") int postId,
            @AuthenticationPrincipal User userPrincipal) throws JsonProcessingException {
        var result = likePostService.likePost(postId, userPrincipal);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/{postId}/likes")
    @ResponseBody
    public ResponseEntity<SuccessResponse<Boolean>> unLikePost(@PathVariable("postId") int postId,
            @AuthenticationPrincipal User userPrincipal) {
        var result = likePostService.unLikePost(postId, userPrincipal);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/{postSlug}/liked")
    @ResponseBody
    public ResponseEntity<SuccessResponse<Boolean>> checkUserLikedPost(@PathVariable("postSlug") String postSlug,
            @AuthenticationPrincipal User userPrincipal) {
        var result = likePostService.checkUserLikedPost(postSlug, userPrincipal);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/{postId}/likes")
    @ResponseBody
    public ResponseEntity<SuccessResponse<List<UserDetailVm>>> getListUserLikedPost(
            @PathVariable("postId") int postId) {
        var result = likePostService.getListUserLikedPost(postId);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
