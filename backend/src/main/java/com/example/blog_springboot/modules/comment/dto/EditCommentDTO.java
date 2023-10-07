package com.example.blog_springboot.modules.comment.dto;


import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

// /api/comments/{postid}/posts
public class EditCommentDTO {
    @NotBlank(message = "Không được bỏ trống trường 'content'")
    @NotNull(message = "Không được thiếu trường 'content'")
    @Length(max = 300,message = "Độ dài trường 'content' tối đa là 300 ký tự")
    private String content;

    @NotBlank(message = "Không được bỏ trống trường 'id'")
    @NotNull(message = "Không được thiếu trường 'id'")
    @Digits(integer = 10,fraction = 0,message = "Trường 'id' phải là số nguyên")
    private int id;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
