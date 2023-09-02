package com.example.blog_springboot.modules.user.service;

import com.example.blog_springboot.exceptions.NotFoundException;
import com.example.blog_springboot.modules.user.model.User;
import com.example.blog_springboot.modules.user.repository.UserRepository;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService{

    final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    @Override
    public User findById(int id) {
        User userFound = userRepository.findById(id).orElse(null);

        if(userFound == null){
            throw new NotFoundException("Người dùng không tồn tại");
        }

        return userFound;
    }

    @Override
    public User findByUserName(String userName) {
        User userFound = userRepository.findByUserName(userName).orElse(null);

        if(userFound == null){
            throw new NotFoundException("Người dùng không tồn tại");
        }

        return userFound;
    }

    @Override
    public User findByEmail(String email) {
        User userFound = userRepository.findByEmail(email).orElse(null);

        if(userFound == null){
            throw new NotFoundException("Người dùng không tồn tại");
        }

        return userFound;
    }
}
