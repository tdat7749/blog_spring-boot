package com.example.blog_springboot.modules.series.DTO;

public class UpdateSeriesDTO {
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public UpdateSeriesDTO(){

    }

    public UpdateSeriesDTO(String title) {
        this.title = title;
    }
}
