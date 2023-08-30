package com.example.blog_springboot.modules.series.ViewModel;

import com.example.blog_springboot.modules.user.Model.User;

public class SeriesVm {
    private int id;
    private String title;

    public SeriesVm(){

    }

    public SeriesVm(int id, String title) {
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
