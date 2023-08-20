package com.example.blog_springboot.modules.comment.DTO;


public class CreateComment {

    private String content;
    private int parentId;
    private int postId;
    public String getContent() {
        return content;
    }

    public int getParentId() {
        return parentId;
    }

    public int getPostId() {
        return postId;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }



    public CreateComment(String content, int parentId,int postId) {
        this.content = content;
        this.parentId = parentId;
        this.postId = postId;
    }
}
