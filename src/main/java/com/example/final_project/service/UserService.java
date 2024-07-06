package com.example.final_project.service;

import com.example.final_project.configuration.MapperUtil;
import com.example.final_project.dto.requestDto.UserLoginRequestDto;
import com.example.final_project.dto.requestDto.UserRegisterRequestDto;
import com.example.final_project.dto.requestDto.UserUpdateRequestDto;
import com.example.final_project.dto.responsedDto.UserResponseDto;
import com.example.final_project.entity.Cart;
import com.example.final_project.entity.User;
import com.example.final_project.entity.enums.Role;
import com.example.final_project.exceptions.AuthorizationException;
import com.example.final_project.exceptions.BadRequestException;
import com.example.final_project.exceptions.NoUsersFoundException;
import com.example.final_project.exceptions.NotFoundInDbException;
import com.example.final_project.mapper.Mappers;
import com.example.final_project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final Mappers mappers;

    public UserResponseDto registerUserProfile(UserRegisterRequestDto userRequestDto) {
        logger.info("Starting registration for user with email: {} and phone: {}", userRequestDto.getEmail(),
                userRequestDto.getPhoneNumber());
        if (userRepository.existsByEmail(userRequestDto.getEmail()) ||
                userRepository.existsByPhoneNumber(userRequestDto.getPhoneNumber())) {
            logger.error("User with email {} and phone {} already exists.", userRequestDto.getEmail(),
                    userRequestDto.getPhoneNumber());
            throw new BadRequestException("User already exists.");
        }

        User user = mappers.convertToUser(userRequestDto);

        user.setRole(Role.USER);
        Cart cart = new Cart();
        cart.setUser(user);
        user.setCart(cart);

        logger.info("User object created for email: {} and phone: {}", userRequestDto.getEmail(),
                userRequestDto.getPhoneNumber());

        User savedUser;
        try {
            savedUser = userRepository.save(user);
            logger.info("User with email: {} successfully saved to the database.", userRequestDto.getEmail());
        } catch (Exception exception) {
            logger.error("Error saving user in DB.", exception);
            throw new AuthorizationException("Error saving users.");
        }

        return mappers.convertToUserResponseDto(savedUser);
    }

    public UserResponseDto loginUserProfile(UserLoginRequestDto userLoginRequestDto) {
        logger.info("Attempting login for user with email: {}", userLoginRequestDto.getEmail());

        if (!userRepository.existsByEmail(userLoginRequestDto.getEmail())) {
            logger.error("User with email {} does not exist.", userLoginRequestDto.getEmail());
            throw new AuthorizationException("User not found.");
        }

        User user = userRepository.findByEmailAndPassword(userLoginRequestDto.getEmail(),
                        userLoginRequestDto.getPassword())
                .orElseThrow(() -> {
                    logger.error("Incorrect password for user with email: {}", userLoginRequestDto.getEmail());
                    return new AuthorizationException("Incorrect password. Please try again.");
                });

        logger.info("User with email: {} successfully logged in.", userLoginRequestDto.getEmail());

        return mappers.convertToUserResponseDto(user);
    }

    public UserResponseDto updateUserProfile(UserUpdateRequestDto userUpdateRequestDto, Long id) {
        logger.info("Attempting to update profile for user with ID: {}", id);

        User user = userRepository.findById(id).orElseThrow(() -> {
            logUserNotFoundError(id);
            return new NotFoundInDbException("Incorrect id in DB.");
        });

        logger.info("Updating user details for user with ID: {}", id);
        user.setName(userUpdateRequestDto.getName());
        user.setPhoneNumber(userUpdateRequestDto.getPhone());

        User savedUser;
        try {
            logger.info("Saving updated user details for user with ID: {}", id);
            savedUser = userRepository.save(user);
        } catch (Exception exception) {
            logger.error("Error saving user with ID: {}", id, exception);
            throw new BadRequestException("Error saving user.");
        }

        logger.info("User with ID: {} successfully updated.", id);
        return mappers.convertToUserResponseDto(savedUser);
    }

    public void deleteUserProfileById(Long id) {
        logger.info("Attempting to delete user profile with ID: {}", id);

        User user = userRepository.findById(id).orElseThrow(() -> {
            logUserNotFoundError(id);
            return new NotFoundInDbException("Incorrect id of user.");
        });

        try {
            logger.info("Deleting user profile with ID: {}", id);
            userRepository.delete(user);
            logger.info("User profile with ID: {} successfully deleted.", id);
        } catch (Exception exception) {
            logger.error("Error deleting user profile with ID: {}", id, exception);
            throw new RuntimeException("Error deleting user profile.");
        }
    }

    public List<UserResponseDto> getAllUsers() {
        logger.info("Attempting to fetch all users.");

        List<User> usersList = userRepository.findAll();

        if (usersList.isEmpty()) {
            logger.info("No users found in the database.");
            throw new NoUsersFoundException("No users found.");
        }

        logger.info("Found {} users.", usersList.size());

        return MapperUtil.convertList(usersList, mappers::convertToUserResponseDto);
    }

    public UserResponseDto getUserById(Long id) {
        logger.info("Attempting to fetch user with ID {}.", id);

        User user = userRepository.findById(id).orElseThrow(() -> {
            logger.info("User with ID {} not found.", id);
            return new NotFoundInDbException("Incorrect id of user.");
        });

        UserResponseDto userResponseDto = mappers.convertToUserResponseDto(user);

        logger.info("User with ID {} successfully fetched.", id);

        return userResponseDto;
    }

    private void logUserNotFoundError(Long id) {
        logger.error("User with ID {} not found in DB.", id);
    }
}
