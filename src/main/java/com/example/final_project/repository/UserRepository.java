package com.example.final_project.repository;

import com.example.final_project.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Finds a {@link User} by their email address.
     *
     * @param email The email address of the user to be found.
     * @return An Optional containing the found User, or an empty Optional if no user is found with the given email address.
     */
    Optional<User> getByEmail(String email);
}
