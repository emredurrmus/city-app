package com.library.meetapp.abstractcore;

import com.library.meetapp.entity.User;
import jakarta.persistence.MappedSuperclass;

import java.time.LocalDateTime;

@SuppressWarnings("serial")
@MappedSuperclass
public abstract class AbstractTrackedEntity extends  AbstractEntity{

    private String createdBy;
    private String updatedBy;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public void setSavedBy(User user) {
        if(getId() != null && getId() > 0) {
            setUpdatedBy(user.getUserName());
            setUpdatedAt(LocalDateTime.now());
        } else {
            setCreatedBy(user.getUserName());
            setCreatedAt(LocalDateTime.now());
        }
    }


    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
