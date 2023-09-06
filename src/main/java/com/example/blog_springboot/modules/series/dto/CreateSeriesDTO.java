package com.example.blog_springboot.modules.series.dto;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public class CreateSeriesDTO {

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

    @NotNull(message = "Không được thiếu trường 'userID'")
    @Digits(integer = 10,fraction = 0,message = "Trường 'userID' phải là số nguyên") // Kiểm tra là số nguyên và ít hơn 10 chữ số
    private int userId;

    public CreateSeriesDTO(){
    }

    public CreateSeriesDTO(String title, String content, String slug, int userId) {
        this.title = title;
        this.content = content;
        this.slug = slug;
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public int getUserId() {
        return userId;
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

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }
}
