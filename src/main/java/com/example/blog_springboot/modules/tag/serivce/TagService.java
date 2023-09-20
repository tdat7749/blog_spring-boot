package com.example.blog_springboot.modules.tag.serivce;

import com.example.blog_springboot.commons.SuccessResponse;
import com.example.blog_springboot.modules.tag.dto.CreateTagDTO;
import com.example.blog_springboot.modules.tag.dto.UpdateTagDTO;
import com.example.blog_springboot.modules.tag.model.Tag;
import com.example.blog_springboot.modules.tag.viewmodel.TagVm;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TagService {
    SuccessResponse<TagVm> getTagById(int id);

    SuccessResponse<List<TagVm>> getAllTag();

    SuccessResponse<Tag> createTag(CreateTagDTO dto);

    SuccessResponse<TagVm> updateTag(UpdateTagDTO dto,int tagId);

    SuccessResponse<Boolean> deleteTag(int tagId);
}
