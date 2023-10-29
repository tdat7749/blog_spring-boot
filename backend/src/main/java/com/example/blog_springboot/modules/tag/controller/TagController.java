package com.example.blog_springboot.modules.tag.controller;

import com.example.blog_springboot.commons.Constants;
import com.example.blog_springboot.commons.PagingResponse;
import com.example.blog_springboot.commons.SuccessResponse;
import com.example.blog_springboot.modules.tag.dto.CreateTagDTO;
import com.example.blog_springboot.modules.tag.dto.UpdateTagDTO;
import com.example.blog_springboot.modules.tag.model.Tag;
import com.example.blog_springboot.modules.tag.serivce.TagService;
import com.example.blog_springboot.modules.tag.viewmodel.TagVm;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tags")
public class TagController {
    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping("/{slug}")
    @ResponseBody
    public ResponseEntity<SuccessResponse<TagVm>> getTagBySlug(@PathVariable("slug") String slug) {
        var result = tagService.getTagBySlug(slug);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/all")
    @ResponseBody
    public ResponseEntity<SuccessResponse<List<TagVm>>> getAllTag() {
        var result = tagService.getAllTag();

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/")
    @ResponseBody
    public ResponseEntity<SuccessResponse<Tag>> createTag(@RequestBody @Valid CreateTagDTO dto) {
        var result = tagService.createTag(dto);

        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @ResponseBody
    public ResponseEntity<SuccessResponse<TagVm>> updateTag(@RequestBody @Valid UpdateTagDTO dto,
            @PathVariable("id") int id) {
        var result = tagService.updateTag(dto, id);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    @ResponseBody
    public ResponseEntity<SuccessResponse<Boolean>> deleteTag(@PathVariable("id") int id) {
        var result = tagService.deleteTag(id);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/")
    @ResponseBody
    public ResponseEntity<SuccessResponse<PagingResponse<List<TagVm>>>> getListTag(
            @RequestParam(name = "keyword", required = false, defaultValue = "") String keyword,
            @RequestParam(name = "pageIndex", required = true, defaultValue = "0") Integer pageIndex,
            @RequestParam(name = "sortBy", required = false, defaultValue = Constants.SORT_BY_CREATED_AT) String sortBy) {
        var result = tagService.getListTag(pageIndex, keyword, sortBy);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
