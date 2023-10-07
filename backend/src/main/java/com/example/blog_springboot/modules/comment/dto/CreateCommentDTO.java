package com.example.blog_springboot.modules.comment.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

// /api/comments/{post_id}/posts
public class CreateCommentDTO {
    private Integer parentId;
    @NotBlank(message = "Không được bỏ trống trường 'content'")
    @NotNull(message = "Không được thiếu trường 'content'")
    @Length(max = 300,message = "Độ dài trường 'content' tối đa là 300 ký tự")
    private String content;

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
