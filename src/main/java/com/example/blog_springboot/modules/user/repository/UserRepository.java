package com.example.blog_springboot.modules.user.repository;

import com.example.blog_springboot.modules.user.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    Optional<User> findByUserName(String userName);
    Optional<User> findByEmail(String email);
    Optional<User> findByEmailAndCode(String email,String code);

    @Query("Select u.following from User as u where u.id = :userId")
    Page<User> getAllUserFollowing(@Param("userId") int userId, Pageable pageable);

    @Query("Select u.followers from User as u where u.id = :userId")
    Page<User> getAllUserFollower(@Param("userId") int userId, Pageable pageable);

}
