package com.library.meetapp.repository;

import com.library.meetapp.entity.Post;
import com.library.meetapp.entity.Taste;
import com.library.meetapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TasteRepository extends JpaRepository<Taste,Long> {


    int countByPost(Post post);

    Taste findTasteByPost(Post post);

    @Query("from Taste t where t.post = (?1)")
    List<Taste> findTastesByPost(Post post);

    Taste findTasteByUserAndPost(User user, Post post);

}
