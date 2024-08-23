package com.library.meetapp.service;

import com.library.meetapp.entity.User;
import com.library.meetapp.repository.UserRepository;
import com.library.meetapp.util.AppCtx;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public User createUser(String userName, String firstName, String lastName, String password) {
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setUserName(userName);
        user.setPassword(passwordEncoder.encode(password));
        user.setCreatedAt(LocalDateTime.now());

        return userRepository.save(user);
    }


    public User save(User user, String pw) {
        if(pw != null && !pw.isEmpty()) {
            user.setPassword(passwordEncoder.encode(pw));
        }
        return userRepository.save(user);
    }


    public User findByUserName(String userName) {

        return userRepository.findByUserNameIgnoreCase(userName);
    }


}
