package com.example.final_project.repository;

import com.example.final_project.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void testGetByEmail_UserExists() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setPasswordHash("encodedPassword");
        userRepository.save(user);

        Optional<User> foundUser = userRepository.getByEmail("test@example.com");

        assertTrue(foundUser.isPresent());
        assertEquals("test@example.com", foundUser.get().getEmail());
    }

    @Test
    void testGetByEmail_UserDoesNotExist() {
        Optional<User> foundUser = userRepository.getByEmail("nonexistent@example.com");

        assertFalse(foundUser.isPresent());
    }
}