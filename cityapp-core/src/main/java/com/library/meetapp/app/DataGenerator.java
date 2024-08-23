package com.library.meetapp.app;


import com.library.meetapp.entity.User;
import com.library.meetapp.repository.UserRepository;
import com.library.meetapp.util.AppCtx;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataGenerator implements HasLogger{


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    private User systemUser;


    @PostConstruct
    public void createData() {

        if (userRepository.count() != 0L) {
            getLogger().info("Using existing database");
            return;
        }

        systemUser = new User();
        systemUser.setUserName("SYSTEM");

        createUsers();

    }


    private void createUsers() {
        createUser("emre","emre", "durmus","emre135");
    }



    private User createUser(String username, String firstname, String lastname, String pw) {
        User user = new User();
        user.setUserName(username);
        user.setFirstName(firstname);
        user.setLastName(lastname);
        user.setPassword(passwordEncoder.encode(pw));
        user.setSavedBy(systemUser);
        return userRepository.save(user);
    }



}
