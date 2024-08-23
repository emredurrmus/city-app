package com.library.meetapp.entity;

import com.library.meetapp.abstractcore.AbstractTrackedEntity;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
public class User extends AbstractTrackedEntity {

    private String firstName;
    private String lastName;

    private LocalDateTime lastLogin;

    private String userName;

    private String password;
    private String imagePath;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDateTime getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(LocalDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getUserName() {
        return userName;
    }


    public String getFullName() {
        String fullName = Arrays.asList(firstName,lastName).stream().filter(str -> str != null).collect(Collectors.joining(" "));
        return fullName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
