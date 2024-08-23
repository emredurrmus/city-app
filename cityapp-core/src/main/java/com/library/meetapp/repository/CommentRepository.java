package com.library.meetapp.repository;

import com.library.meetapp.entity.Comment;
import com.library.meetapp.entity.Post;
import org.hibernate.annotations.Comments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByPostId(Long postId);

    @Query("from Comment c where c.post = (?1)")
    List<Comment> findCommentsByPost(Post post);


    boolean existsByPostId(Long id);

}
