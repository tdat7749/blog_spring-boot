package com.example.blog_springboot.modules.post.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class AddPostToSeriesDTO {

    @NotBlank(message = "Không được bỏ trống trường 'postId'")
    @NotNull(message = "Không được thiếu trường 'postId'")
    @Digits(integer = 10,fraction = 0,message = "Trường 'postId' phải là số nguyên")
    private int postId;

    @NotBlank(message = "Không được bỏ trống trường 'seriesId'")
    @NotNull(message = "Không được thiếu trường 'seriesId'")
    @Digits(integer = 10,fraction = 0,message = "Trường 'seriesId' phải là số nguyên")
    private int seriesId;
}
