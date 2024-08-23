package com.library.meetapp.repository;

import com.library.meetapp.entity.Notification;
import com.library.meetapp.entity.Post;
import com.library.meetapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findNotificationsByPostId(Long notificationId);
    List<Notification> findNotificationsByUserOrderByIdDesc(User user);

    Notification findNotificationByUserAndPost(User user, Post post);


    boolean existsByPostId(Long id);
}
