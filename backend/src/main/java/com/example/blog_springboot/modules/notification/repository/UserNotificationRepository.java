package com.example.blog_springboot.modules.notification.repository;

import com.example.blog_springboot.modules.notification.model.UserNotification;
import com.example.blog_springboot.modules.user.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserNotificationRepository extends JpaRepository<UserNotification,Integer> {
    @Query("select un from UserNotification as un left join un.notification as n where un.user = :user order by n.createdAt desc")
    List<UserNotification> getTop10NotificationCurrentUser(User user, Pageable pageable);

    @Query("select un from UserNotification as un left join un.notification as n where un.user = :user order by n.createdAt desc")
    Page<UserNotification> getNotificationCurrentUser(User user, Pageable pageable);

    @Query("select count(un.id) from UserNotification as un where un.user = :user and un.isRead = false")
    int countUnReadNotification(User user);

    Optional<UserNotification> findByUserAndId(User user,int id);

    @Query("update UserNotification as un set un.isRead = true where un.user = :user")
    @Modifying
    void readAllNotification(User user);
}
