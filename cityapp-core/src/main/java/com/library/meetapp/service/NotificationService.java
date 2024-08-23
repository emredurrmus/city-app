package com.library.meetapp.service;

import com.library.meetapp.entity.Notification;
import com.library.meetapp.entity.Post;
import com.library.meetapp.entity.User;
import com.library.meetapp.repository.NotificationRepository;
import org.aspectj.weaver.ast.Not;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepo;


    public Notification createCommentNotifications(User currentUser, Post post) {
        Notification notification = new Notification();
        notification.setPost(post);
        notification.setUser(post.getUser());
        notification.setAuthorUser(currentUser);
        notification.setCreatedAt(LocalDateTime.now());
        notification.setSubject("Yorum Bildirimi");
        notification.setDescription(String.format("%s kullanıcısı %s içeriğinize yorum attı", currentUser.getFullName(), post.getTitle()));
        notification.setType(post.getCategory());
        return notificationRepo.save(notification);
    }


    public Notification createLikeNotifications(User currentUser, Post post) {
        Notification notification = new Notification();
        notification.setPost(post);
        notification.setUser(post.getUser());
        notification.setAuthorUser(currentUser);
        notification.setCreatedAt(LocalDateTime.now());
        notification.setSubject("Beğeni Bildirimi");
        notification.setDescription(String.format("%s kullanıcısı %s içeriğinizi beğendi", currentUser.getFullName(), post.getTitle()));
        notification.setType(post.getCategory());


        return notificationRepo.save(notification);
    }

    public List<Notification> getNotifications(User currentUser) {
        return notificationRepo.findNotificationsByUserOrderByIdDesc(currentUser);
    }


    public void deleteNotification(User currentUser, Post post) {
        Notification notification = notificationRepo.findNotificationByUserAndPost(currentUser, post);

        notificationRepo.delete(notification);
    }


    public void deleteNotification(Long notificationId) {
        List<Notification> notifications = notificationRepo.findNotificationsByPostId(notificationId);
        if (notifications.isEmpty()) {
            throw new NoSuchElementException("Notification not found");
        }
        notificationRepo.deleteAll(notifications);
    }

    public boolean existsNotificationForPost(Long postId) {
        return notificationRepo.existsByPostId(postId); // Veritabanında ilgili postId'ye sahip bildirim var mı diye kontrol et ve sonucu döndür
    }

}
