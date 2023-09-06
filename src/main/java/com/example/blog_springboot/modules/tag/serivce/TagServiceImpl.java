package com.example.blog_springboot.modules.tag.serivce;

import com.example.blog_springboot.commons.SuccessResponse;
import com.example.blog_springboot.exceptions.NotFoundException;
import com.example.blog_springboot.modules.tag.dto.CreateTagDTO;
import com.example.blog_springboot.modules.tag.dto.UpdateTagDTO;
import com.example.blog_springboot.modules.tag.model.Tag;
import com.example.blog_springboot.modules.tag.repository.TagRepository;
import com.example.blog_springboot.modules.tag.viewmodel.TagVm;

import java.util.List;

public class TagServiceImpl implements TagService{

    private final TagRepository tagRepository;

    public TagServiceImpl(TagRepository tagRepository){
        this.tagRepository = tagRepository;
    }

    @Override
    public SuccessResponse<TagVm> getTagById(int id) {
        var foundTag = tagRepository.findById(id).orElse(null);
        if(foundTag == null){
            throw new NotFoundException("Không tìm thấy tag với id = " + id);
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
        return null;
    }

    @Override
    public SuccessResponse<TagVm> updateTag(UpdateTagDTO dto) {
        return null;
    }

    @Override
    public SuccessResponse<TagVm> deleteTag() {
        return null;
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
