package com.example.blog_springboot.modules.user.service;

import com.example.blog_springboot.modules.user.model.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    public User findById(int id);
    public User findByUserName(String userName);
    public User findByEmail(String email);
}
