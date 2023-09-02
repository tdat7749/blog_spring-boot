package com.example.blog_springboot.modules.tag.ViewModel;

public class TagVm {
    private int id;
    private String title;

    public TagVm(){

    }

    public TagVm(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
