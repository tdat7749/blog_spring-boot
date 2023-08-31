package com.example.blog_springboot.modules.user.Repository;

import com.example.blog_springboot.modules.user.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {
}
