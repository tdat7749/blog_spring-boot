package com.example.blog_springboot.modules.posttags.Model;

import com.example.blog_springboot.modules.post.Model.Post;
import com.example.blog_springboot.modules.tag.model.Tag;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name = "posttags")
public class PostTags {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // Config ORM
    @ManyToOne
    @JoinColumn(name = "post_id",nullable = false)
    @JsonBackReference
    private Post post;

    @ManyToOne
    @JoinColumn(name = "tag_id",nullable = false)
    @JsonBackReference
    private Tag tag;

    public PostTags(){

    }
    public PostTags(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public Post getPost() {
        return post;
    }

    public Tag getTag() {
        return tag;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }
}
