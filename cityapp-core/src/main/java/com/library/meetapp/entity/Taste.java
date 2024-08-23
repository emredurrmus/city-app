package com.library.meetapp.entity;


import com.library.meetapp.abstractcore.AbstractTrackedEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;

@Entity
public class Taste extends AbstractTrackedEntity {

    @ManyToOne
    private User user;

    @ManyToOne
    private Post post;


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}
