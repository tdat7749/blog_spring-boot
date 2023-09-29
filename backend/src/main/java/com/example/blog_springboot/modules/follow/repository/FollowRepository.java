package com.example.blog_springboot.modules.follow.repository;

import com.example.blog_springboot.modules.follow.model.Follow;
import com.example.blog_springboot.modules.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FollowRepository extends JpaRepository<Follow,Integer> {

    Optional<Follow> findByFollowersAndFollowing(User follower, User following);
}
