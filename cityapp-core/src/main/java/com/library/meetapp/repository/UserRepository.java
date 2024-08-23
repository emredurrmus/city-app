package com.library.meetapp.repository;

import com.library.meetapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUserNameIgnoreCase(String username);

    @Query("FROM User u WHERE u.id = (?1)")
    User findWithPropertyPictureAttachedById(Long id);

}
