package com.example.final_project.repository;

import com.example.final_project.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> getByEmail(String email);


//    @Query("SELECT u FROM User u WHERE u.email = :email AND u.passwordHash = :password")
//    Optional<User> findByEmailAndPassword(String email, String password);

    @Query("SELECT u from User u WHERE u.email = :email")
    Optional<User> findByEmail(String email);

}
