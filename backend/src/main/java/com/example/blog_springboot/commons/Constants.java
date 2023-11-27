package com.example.blog_springboot.commons;

public class Constants {

    //Cloudinary config
    public static final String CLOUD_NAME = "dafpvnxnf";

    public static final String API_KEY = "361155637793245";

    public static final String API_SECRET = "_TEg9LCDNiT1r79EnD_5HQ7XU0g";

    public static final Integer PAGE_SIZE = 10;
    public static final String SORT_BY_CREATED_AT = "createdAt";

    public static final long MAX_FILE = 1024 * 1024 * 3;

    public static final String[] MIME_TYPES = {"image/png","image/jpeg","image/webp","image/gif"};

    public static final String FROM_EMAIL = "noreply@bloghayhay.info";

    public static final String SUBJECT_EMAIL_VERIFY = "MÃ XÁC MINH TÀI KHOẢN CỦA BẠN";

    public static final String SUBJECT_EMAIL_FORGOT_PASSWORD = "MÃ XÁC MINH QUÊN MẬT KHẨU";

    public static final String SUBJECT_EMAIL_UNLOCK_ACCOUNT = "MỞ KHÓA TÀI KHOẢN";

    public static final String SUBJECT_EMAIL_LOCK_ACCOUNT = "KHÓA TÀI KHOẢN";

    public static final String PUBLIC_HOST = "http://localhost:4200";

    public static final String PRIVATE_CHANNEL_WEBSOCKET = "/user/queue/notification";

}
