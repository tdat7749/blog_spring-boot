package com.example.blog_springboot.modules.user.repository;

import com.example.blog_springboot.commons.PagingRequestDTO;
import com.example.blog_springboot.modules.user.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    Optional<User> findByUserName(String userName);
    Optional<User> findByEmail(String email);
    Optional<User> findByEmailAndCode(String email,String code);

    Page<User> getAllUserFollowing(PagingRequestDTO dto);
}
