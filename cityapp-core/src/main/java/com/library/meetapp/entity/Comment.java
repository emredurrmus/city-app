package com.library.meetapp.entity;

import com.library.meetapp.abstractcore.AbstractTrackedEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;


@Entity
public class Comment extends AbstractTrackedEntity {

    @ManyToOne
    private Post post;

    @ManyToOne
    private User authorComment;


    private String comment;


    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public User getAuthorComment() {
        return authorComment;
    }

    public void setAuthorComment(User authorComment) {
        this.authorComment = authorComment;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
