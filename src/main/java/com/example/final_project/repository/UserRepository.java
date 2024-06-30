package com.example.final_project.repository;

import com.example.final_project.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);

    @Query("SELECT u FROM User u WHERE u.email = :email AND u.passwordHash = :password")
    Optional<User> findByEmailAndPassword(String email, String password);

}
