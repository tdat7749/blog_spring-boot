package com.example.blog_springboot.modules.post.dto;

import com.example.blog_springboot.modules.tag.dto.CreateTagDTO;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.util.List;

public class UpdatePostDTO {
    @NotBlank(message = "Không được bỏ trống trường 'title'")
    @NotNull(message = "Không được thiếu trường 'title'")
    @Length(max = 100,message = "Độ dài trường 'title' tối đa là 100 ký tự")
    private String title;

    @NotBlank(message = "Không được bỏ trống trường 'content'")
    @NotNull(message = "Không được thiếu trường 'content'")
    private String content;

    @NotBlank(message = "Không được bỏ trống trường 'slug'")
    @NotNull(message = "Không được thiếu trường 'slug'")
    @Length(max = 120,message = "Độ dài trường 'slug' tối đa là 120 ký tự")
    private String slug;

    @NotBlank(message = "Không được bỏ trống trường 'summary'")
    @NotNull(message = "Không được thiếu trường 'summary'")
    private String summary;

    private String thumbnail;

    @Digits(integer = 10,fraction = 0,message = "Trường 'seriesID' phải là số nguyên") // Kiểm tra là số nguyên và ít hơn 10 chữ số
    private Integer seriesId;


    @NotNull(message = "Không được thiếu trường 'listTags'")
    private List<CreateTagDTO> listTags;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public Integer getSeriesId() {
        return seriesId;
    }

    public void setSeriesId(Integer seriesId) {
        this.seriesId = seriesId;
    }


    public List<CreateTagDTO> getListTags() {
        return listTags;
    }

    public void setListTags(List<CreateTagDTO> listTags) {
        this.listTags = listTags;
    }
}
