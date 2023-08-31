package com.example.blog_springboot.modules.post.ViewModel;

import com.example.blog_springboot.modules.tag.ViewModel.TagVm;

import java.util.List;

public class PostListVm {
    private String title;
    private String slug;
    private int id;
    private String createdAt;
    private String updatedAt;
    private String firstName;
    private String lastName;
    private List<TagVm> tags;
}
