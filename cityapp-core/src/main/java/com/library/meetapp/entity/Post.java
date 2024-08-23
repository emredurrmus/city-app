package com.library.meetapp.entity;

import com.library.meetapp.abstractcore.AbstractTrackedEntity;
import jakarta.persistence.*;


@Entity
public class Post extends AbstractTrackedEntity {

    @ManyToOne
    private User user;


    private String title;

    @Column(length = 4000)
    private String content;

    private String category;


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getUserFullName() {
        return user.getFullName();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

}
