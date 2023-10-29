package com.example.blog_springboot.modules.tag.serivce;

import com.example.blog_springboot.commons.Constants;
import com.example.blog_springboot.commons.PagingResponse;
import com.example.blog_springboot.commons.SuccessResponse;
import com.example.blog_springboot.modules.post.model.Post;
import com.example.blog_springboot.modules.post.viewmodel.PostListVm;
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
import com.example.blog_springboot.utils.Utilities;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;

    public TagServiceImpl(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    public SuccessResponse<TagVm> getTagBySlug(String slug) {
        var foundTag = tagRepository.findBySlug(slug).orElse(null);
        if (foundTag == null) {
            throw new TagNotFoundException(TagConstants.TAG_NOT_FOUND);
        }
        var tagVm = Utilities.getTagVm(foundTag);

        return new SuccessResponse<>("Thành công", tagVm);
    }

    @Override
    public SuccessResponse<List<TagVm>> getAllTag() {
        var listTags = tagRepository.findAll();

        List<TagVm> listTagsVm = listTags.stream().map(Utilities::getTagVm).toList();

        return new SuccessResponse<>("Thành công", listTagsVm);
    }

    @Override
    public SuccessResponse<Tag> createTag(CreateTagDTO dto) {
        var foundTag = tagRepository.findBySlug(dto.getSlug()).orElse(null);
        if (foundTag != null) {
            throw new TagSlugDuplicateException(TagConstants.TAG_SLUG_DUPLICATE);
        }

        Tag newTag = new Tag();
        newTag.setSlug(dto.getSlug());
        newTag.setTitle(dto.getTitle());
        newTag.setStatus(true);
        newTag.setCreatedAt(new Date());
        newTag.setUpdatedAt(new Date());

        var createTag = tagRepository.save(newTag);
        if (createTag == null) {
            throw new CreateTagException(TagConstants.CREATE_TAG_FAILED);
        }
        return new SuccessResponse<>(TagConstants.CREATE_TAG_SUCCESS, createTag);
    }

    @Override
    public SuccessResponse<TagVm> updateTag(UpdateTagDTO dto, int tagId) {
        var foundTag = tagRepository.findById(tagId).orElse(null);
        if (foundTag == null) {
            throw new TagNotFoundException(TagConstants.TAG_NOT_FOUND);
        }

        var foundTagBySlug = tagRepository.findBySlug(dto.getSlug()).orElse(null);

        if (foundTagBySlug != null && foundTag != foundTagBySlug) {
            throw new TagSlugDuplicateException(TagConstants.TAG_SLUG_DUPLICATE);
        }
        foundTag.setSlug(dto.getSlug());
        foundTag.setTitle(dto.getTitle());
        foundTag.setUpdatedAt(new Date());

        var updateTag = tagRepository.save(foundTag);
        if (updateTag == null) {
            throw new UpdateTagException(TagConstants.UPDATE_TAG_FAILED);
        }

        var tagVm = Utilities.getTagVm(updateTag);
        return new SuccessResponse<>(TagConstants.UPDATE_TAG_SUCCESS, tagVm);

    }

    @Override
    public SuccessResponse<Boolean> deleteTag(int tagId) {
        var foundTag = tagRepository.findById(tagId).orElse(null);
        if (foundTag == null) {
            throw new TagNotFoundException(TagConstants.TAG_NOT_FOUND);
        }

        tagRepository.delete(foundTag);
        return new SuccessResponse<>(TagConstants.DELETE_TAG_SUCCESS, true);

    }

    @Override
    public SuccessResponse<PagingResponse<List<TagVm>>> getListTag(int pageIndex, String keyword, String sortBy) {
        Pageable paging = PageRequest.of(pageIndex, Constants.PAGE_SIZE, Sort.by(Sort.Direction.DESC, sortBy));

        Page<Tag> pagingResult = tagRepository.getListTag(keyword, paging);

        List<TagVm> listTagVm = pagingResult.stream().map(Utilities::getTagVm).toList();

        return new SuccessResponse<>("Thành công",
                new PagingResponse<>(pagingResult.getTotalPages(), (int) pagingResult.getTotalElements(), listTagVm));
    }

}
