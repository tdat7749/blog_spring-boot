package com.example.blog_springboot.modules.tag.serivce;

import com.example.blog_springboot.commons.SuccessResponse;
import com.example.blog_springboot.modules.tag.dto.CreateTagDTO;
import com.example.blog_springboot.modules.tag.dto.UpdateTagDTO;
import com.example.blog_springboot.modules.tag.viewmodel.TagVm;

import java.util.List;

public interface TagService {
    SuccessResponse<TagVm> getTagById(int id);

    SuccessResponse<List<TagVm>> getAllTag();

    SuccessResponse<TagVm> createTag(CreateTagDTO dto);

    SuccessResponse<TagVm> updateTag(UpdateTagDTO dto);

    SuccessResponse<TagVm> deleteTag();
}
