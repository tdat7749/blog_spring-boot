package com.example.blog_springboot.modules.series.DTO;

public class CreateSeriesDTO {
    private String title;
    private int userId;

    public CreateSeriesDTO(){

    }
    public CreateSeriesDTO(String title, int userId) {
        this.title = title;
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public int getUserId() {
        return userId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
