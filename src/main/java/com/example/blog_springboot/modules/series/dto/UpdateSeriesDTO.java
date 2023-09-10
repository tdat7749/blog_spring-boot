package com.example.blog_springboot.modules.series.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public class UpdateSeriesDTO {
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


    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getSlug() {
        return slug;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public UpdateSeriesDTO(){

    }

    public UpdateSeriesDTO(String title, String content, String slug) {
        this.title = title;
        this.content = content;
        this.slug = slug;
    }
}
