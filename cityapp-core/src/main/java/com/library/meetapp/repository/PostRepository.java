package com.library.meetapp.repository;

import com.library.meetapp.entity.Post;
import com.library.meetapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post,Long> {


    @Query("from Post p where p.category = (?1)")
    List<Post> getPostsByCategory(String category);

    List<Post> findPostsByUser(User user);

}
