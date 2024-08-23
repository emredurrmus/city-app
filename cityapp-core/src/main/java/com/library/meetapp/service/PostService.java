package com.library.meetapp.service;


import com.library.meetapp.entity.*;
import com.library.meetapp.repository.CommentRepository;
import com.library.meetapp.repository.PostRepository;
import com.library.meetapp.repository.TasteRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepo;

    @Autowired
    private CommentRepository commentRepo;

    @Autowired
    private NotificationService notificationService;


    @Autowired
    private TasteRepository tasteRepository;


    public Post save(User user,Post post) {
        post.setUser(user);
        post.setCreatedAt(LocalDateTime.now());

        return postRepo.save(post);
    }

    @Transactional
    public Post saveEditPost(User user,Post post) {
        post.setUpdatedAt(LocalDateTime.now());
        post.setUpdatedBy(user.getUserName());
        return postRepo.save(post);
    }


    public void deletePost(Post post) {
        Long postId = post.getId();
        List<Taste> tastes = tasteRepository.findTastesByPost(post);
        if(tastes != null) {
            tasteRepository.deleteAll(tastes);
        }

        if (notificationService.existsNotificationForPost(postId)) {
            notificationService.deleteNotification(postId);
        }
        if (existsCommentsForPost(postId)) {
            deleteCommentsByPostId(postId);
        }
        try {
            postRepo.deleteById(postId);
        } catch (EmptyResultDataAccessException e) {
            System.out.println("No post found with postId: " + postId);
        }
    }

    public boolean existsCommentsForPost(Long postId) {
        return commentRepo.existsByPostId(postId);
    }

    public void deleteCommentsByPostId(Long postId) {
        List<Comment> comments = commentRepo.findByPostId(postId);
        if (comments.isEmpty()) {
            throw new NoSuchElementException("Comments not found for the given postId");
        }
        commentRepo.deleteAll(comments);
    }


    public List<Post> getPostsByCategory(String category) {
        return postRepo.getPostsByCategory(category);
    }


    public Optional<Post> findPostById(Long id) {
        return postRepo.findById(id);
    }



    //Comment
    @Transactional
    public Comment saveComment(User currentUser,Post post, Comment comment) {
        comment.setPost(post);
        comment.setAuthorComment(currentUser);

        notificationService.createCommentNotifications(currentUser, post);
        return commentRepo.save(comment);
    }

    public List<Comment> getCommentsByPost(Post post) {
        return commentRepo.findCommentsByPost(post);
    }

    public List<Taste> getTastesByPost(Post post) {
        return tasteRepository.findTastesByPost(post);
    }


    //Taste

    @Transactional
    public Taste saveLike(User currentUser, Post post) {

        Taste taste = new Taste();
        taste.setCreatedAt(LocalDateTime.now());
        taste.setPost(post);
        taste.setUser(currentUser);

        notificationService.createLikeNotifications(currentUser, post);

        return tasteRepository.save(taste);
    }

    public void removeLike(User currentUser,Post post) {

        Taste taste = tasteRepository.findTasteByPost(post);

        notificationService.deleteNotification(currentUser,post);

        tasteRepository.delete(taste);
    }



    public Taste findTasteByPostAndUserIfExist(User currentUser,Post post) {
        return tasteRepository.findTasteByUserAndPost(currentUser, post);
    }


    public List<Post> getTopLikedPosts() {
        List<Post> posts = postRepo.findAll();
        return posts.stream()
                .sorted((p1, p2) -> Integer.compare(tasteRepository.countByPost(p2), tasteRepository.countByPost(p1)))
                .collect(Collectors.toList());
    }


    public List<Post> getPostsByUser(User user) {
        return postRepo.findPostsByUser(user);
    }





}
