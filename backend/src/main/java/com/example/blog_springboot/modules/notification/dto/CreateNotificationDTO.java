package com.example.blog_springboot.modules.notification.dto;

public class CreateNotificationDTO {
    private String link;
    private String message;

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
