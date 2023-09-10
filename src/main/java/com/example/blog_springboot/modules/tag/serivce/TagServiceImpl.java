package com.example.blog_springboot.modules.tag.serivce;

import com.example.blog_springboot.commons.SuccessResponse;
import com.example.blog_springboot.exceptions.NotFoundException;
import com.example.blog_springboot.modules.tag.constant.TagConstants;
import com.example.blog_springboot.modules.tag.dto.CreateTagDTO;
import com.example.blog_springboot.modules.tag.dto.UpdateTagDTO;
import com.example.blog_springboot.modules.tag.exception.CreateTagException;
import com.example.blog_springboot.modules.tag.exception.TagNotFoundException;
import com.example.blog_springboot.modules.tag.exception.TagSlugDuplicateException;
import com.example.blog_springboot.modules.tag.exception.UpdateTagException;
import com.example.blog_springboot.modules.tag.model.Tag;
import com.example.blog_springboot.modules.tag.repository.TagRepository;
import com.example.blog_springboot.modules.tag.viewmodel.TagVm;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TagServiceImpl implements TagService{

    private final TagRepository tagRepository;

    public TagServiceImpl(TagRepository tagRepository){
        this.tagRepository = tagRepository;
    }

    @Override
    public SuccessResponse<TagVm> getTagById(int id) {
        var foundTag = tagRepository.findById(id).orElse(null);
        if(foundTag == null){
            throw new TagNotFoundException(TagConstants.TAG_NOT_FOUND);
        }
        var tagVm = getTagVm(foundTag);

        return new SuccessResponse<>("Thành công",tagVm);
    }

    @Override
    public SuccessResponse<List<TagVm>> getAllTag() {
        var listTags = tagRepository.findAll();

        List<TagVm> listTagsVm = listTags.stream().map(this::getTagVm).toList();

        return new SuccessResponse<>("Thành công",listTagsVm);
    }

    @Override
    public SuccessResponse<TagVm> createTag(CreateTagDTO dto) {
        var foundTag = tagRepository.findBySlug(dto.getSlug()).orElse(null);
        if(foundTag != null){
            throw new TagSlugDuplicateException(TagConstants.TAG_SLUG_DUPLICATE);
        }

        Tag newTag = new Tag();
        newTag.setSlug(dto.getSlug());
        newTag.setTitle(dto.getTitle());
        newTag.setThumbnail(dto.getThumbnail());
        newTag.setStatus(true);
        newTag.setCreatedAt(new Date());
        newTag.setUpdatedAt(new Date());

        var createTag = tagRepository.save(newTag);
        if(createTag == null){
            throw new CreateTagException(TagConstants.CREATE_TAG_FAILED);
        }

        var tagVm = getTagVm(createTag);
        return new SuccessResponse<>(TagConstants.CREATE_TAG_SUCCESS,tagVm);
    }

    @Override
    public SuccessResponse<TagVm> updateTag(UpdateTagDTO dto,int tagId) {
        var foundTag = tagRepository.findById(tagId).orElse(null);
        if(foundTag == null){
            throw new TagNotFoundException(TagConstants.TAG_NOT_FOUND);
        }

        var foundTagBySlug = tagRepository.findBySlug(dto.getSlug()).orElse(null);

        if(foundTagBySlug != null && foundTag != foundTagBySlug){
            throw new TagSlugDuplicateException(TagConstants.TAG_SLUG_DUPLICATE);
        }
        foundTag.setSlug(dto.getSlug());
        foundTag.setTitle(dto.getTitle());
        foundTag.setUpdatedAt(new Date());
        if(dto.getThumbnail() != null){
            foundTag.setThumbnail(dto.getThumbnail());
        }

        var updateTag = tagRepository.save(foundTag);
        if(updateTag == null){
            throw new UpdateTagException(TagConstants.UPDATE_TAG_FAILED);
        }

        var tagVm = getTagVm(updateTag);
        return new SuccessResponse<>(TagConstants.UPDATE_TAG_SUCCESS,tagVm);

    }

    @Override
    public SuccessResponse<Boolean> deleteTag(int tagId) {
        var foundTag = tagRepository.findById(tagId).orElse(null);
        if(foundTag == null){
            throw new TagNotFoundException(TagConstants.TAG_NOT_FOUND);
        }

        tagRepository.delete(foundTag);
        return new SuccessResponse<>(TagConstants.DELETE_TAG_SUCCESS,true);

    }

    private TagVm getTagVm(Tag tag){
        TagVm tagVm = new TagVm();
        tagVm.setId(tag.getId());
        tagVm.setTitle(tag.getTitle());
        tagVm.setSlug(tag.getSlug());
        tagVm.setThumbnail(tag.getThumbnail());
        tagVm.setCreatedAt(tag.getCreatedAt().toString());
        tagVm.setUpdatedAt(tag.getUpdatedAt().toString());

        return tagVm;
    }
}
