package com.example.blog_springboot.modules.notification.repository;

import com.example.blog_springboot.modules.notification.model.UserNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserNotificationRepository extends JpaRepository<UserNotification,Integer> {

}
