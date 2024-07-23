package com.example.final_project.service;

import com.example.final_project.configuration.MapperUtil;
import com.example.final_project.dto.requestDto.UserLoginRequestDto;
import com.example.final_project.dto.requestDto.UserRegisterRequestDto;
import com.example.final_project.dto.requestDto.UserUpdateRequestDto;
import com.example.final_project.dto.responsedDto.UserResponseDto;
import com.example.final_project.entity.Cart;
import com.example.final_project.entity.User;
import com.example.final_project.entity.enums.Role;
import com.example.final_project.exceptions.*;
import com.example.final_project.mapper.Mappers;
import com.example.final_project.repository.UserRepository;
import com.example.final_project.security.jwt.JwtAuthentication;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final Mappers mappers;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.admin.emails}")
    @Getter
    @Setter
    private String adminEmails;

    /**
     * Registers a new user profile.
     *
     * @param userRegisterRequestDto DTO containing registration details.
     * @return The registered user response DTO.
     */
    public UserResponseDto registerUserProfile(UserRegisterRequestDto userRegisterRequestDto) {
        logInfo("Starting registration for user with email: {}", userRegisterRequestDto.getEmail());

        // Check if the user already exists.
        User userExisted = userRepository.getByEmail(userRegisterRequestDto.getEmail()).orElse(null);
        if (userExisted != null) {
            logError("User with email {} already exists.", userRegisterRequestDto.getEmail());
            throw new BadRequestException("User already exists.");
        }

        // Convert DTO to User entity and set properties.
        User user = mappers.convertRegisterDTOToUser(userRegisterRequestDto);
        String[] adminEmailList = adminEmails.split(",");
        user.setRole(Role.USER);
        for (String adminEmail : adminEmailList) {
            if (userRegisterRequestDto.getEmail().equals(adminEmail)) {
                user.setRole(Role.ADMINISTRATOR);
                break;
            }
        }
        user.setPasswordHash(passwordEncoder.encode(userRegisterRequestDto.getPasswordHash()));
        Cart cart = new Cart();
        cart.setUser(user);
        user.setCart(cart);

        logInfo("User object created for email: {}", userRegisterRequestDto.getEmail());

        // Save user to the database.
        User savedUser;
        try {
            savedUser = userRepository.save(user);
            logInfo("User with email: {} successfully saved to the database.", savedUser.getEmail());
        } catch (Exception exception) {
            logError("Error saving user in DB.", exception);
            throw new AuthorizationException("Error saving users.");
        }
        return mappers.convertToUserResponseDto(savedUser);
    }

    /**
     * Logs in a user profile.
     *
     * @param userLoginRequestDto DTO containing login credentials.
     * @return The user response DTO.
     */
    public UserResponseDto loginUserProfile(UserLoginRequestDto userLoginRequestDto) {
        logInfo("Attempting login for user with email: {}", userLoginRequestDto.getEmail());

        // Find user by email.
        User user = userRepository.getByEmail(userLoginRequestDto.getEmail())
                .orElseThrow(() -> {
                    logError("User with email {} does not exist.", userLoginRequestDto.getEmail());
                    return new AuthorizationException("User not found.");
                });

        // Check password validity.
        if (!passwordEncoder.matches(userLoginRequestDto.getPassword(), user.getPasswordHash())) {
            logError("Incorrect password for user with email: {}", userLoginRequestDto.getEmail());
            throw new AuthorizationException("Incorrect password. Please try again.");
        }

        logInfo("User with email: {} successfully logged in.", userLoginRequestDto.getEmail());
        return mappers.convertToUserResponseDto(user);
    }

    /**
     * Retrieves a user by email.
     *
     * @param email The email of the user to retrieve.
     * @return The user response DTO.
     */
    public UserResponseDto getByEmail(String email) {
        logInfo("Get user with email: {}", email);
        User user = userRepository.getByEmail(email)
                .orElseThrow(() -> {
                    logError("User with email {} does not exist in DB.", email);
                    return new AuthorizationException("User not found.");
                });

        return mappers.convertToUserResponseDto(user);
    }

    /**
     * Sets a refresh token for the user.
     *
     * @param userResponseDto The user response DTO.
     * @param refreshToken    The refresh token to set.
     */
    public void setRefreshToken(UserResponseDto userResponseDto, String refreshToken) {
        logInfo("Set refreshToken to user with email: {}", userResponseDto.getEmail());
        User user = mappers.convertResponceDTOToUser(userResponseDto);
        user.setRefreshToken(refreshToken);

        try {
            userRepository.save(user);
            logInfo("RefreshToken for User with email: {} successfully saved to the database.", userResponseDto.getEmail());
        } catch (Exception exception) {
            logError("Error saving RefreshToken for user in DB.", exception);
            throw new AuthorizationException("Error saving RefreshToken for users.");
        }
    }

    /**
     * Updates the user profile.
     *
     * @param userUpdateRequestDto DTO containing update details.
     * @param id                   The ID of the user to update.
     * @return The updated user response DTO.
     */
    public UserResponseDto updateUserProfile(UserUpdateRequestDto userUpdateRequestDto, Long id) {
        logInfo("Attempting to update profile for user with ID: {}", id);

        User user = userRepository.findById(id).orElseThrow(() -> {
            logError("User with ID {} not found in DB.", id);
            return new NotFoundInDbException("Incorrect id in DB.");
        });

        logInfo("Updating user details for user with ID: {}", id);
        user.setName(userUpdateRequestDto.getName());
        user.setPhoneNumber(userUpdateRequestDto.getPhone());

        User savedUser;
        try {
            logInfo("Saving updated user details for user with ID: {}", id);
            savedUser = userRepository.save(user);
        } catch (Exception exception) {
            logError("Error saving user with ID: {}", id, exception);
            throw new BadRequestException("Error saving user.");
        }

        logInfo("User with ID: {} successfully updated.", id);
        return mappers.convertToUserResponseDto(savedUser);
    }

    /**
     * Deletes the user profile by ID.
     *
     * @param id The ID of the user to delete.
     */
    public void deleteUserProfileById(Long id) {
        logInfo("Attempting to delete user profile with ID: {}", id);

        User user = userRepository.findById(id).orElseThrow(() -> {
            logError("User with ID {} not found in DB.", id);
            return new NotFoundInDbException("Incorrect id of user.");
        });

        try {
            logInfo("Deleting user profile with ID: {}", id);
            userRepository.delete(user);
            logInfo("User profile with ID: {} successfully deleted.", id);
        } catch (Exception exception) {
            logError("Error deleting user profile with ID: {}", id, exception);
            throw new ErrorParamException("Error deleting user profile.");
        }
    }

    /**
     * Retrieves all users.
     *
     * @return A list of user response DTOs.
     */
    public List<UserResponseDto> getAllUsers() {
        logInfo("Attempting to fetch all users.");
        boolean isAdministrator = false;
        List<User> usersList;
        final JwtAuthentication tokenInfo = (JwtAuthentication) SecurityContextHolder.getContext().getAuthentication();
        Set<SimpleGrantedAuthority> roles = tokenInfo.getRoles();
        for (SimpleGrantedAuthority role : roles) {
            if (role.getAuthority().equals("ROLE_ADMINISTRATOR")) {
                isAdministrator = true;
                break;
            }
        }
        if (isAdministrator) {
            usersList = userRepository.findAll();
        } else {
            throw new AuthorizationException("This role does not have data access.");
        }

        if (usersList.isEmpty()) {
            logInfo("No users found in the database.");
            throw new NoUsersFoundException("No users found.");
        }

        logInfo("Found {} users.", usersList.size());

        return MapperUtil.convertList(usersList, mappers::convertToUserResponseDto);
    }

    /**
     * Retrieves a user by ID.
     *
     * @param id The ID of the user to fetch.
     * @return The user response DTO.
     */
    public UserResponseDto getUserById(Long id) {
        logInfo("Attempting to fetch user with ID {}.", id);

        User user = userRepository.findById(id).orElseThrow(() -> {
            logInfo("User with ID {} not found.", id);
            return new NotFoundInDbException("Incorrect id of user.");
        });

        UserResponseDto userResponseDto = mappers.convertToUserResponseDto(user);

        logInfo("User with ID {} successfully fetched.", id);

        return userResponseDto;
    }

    /**
     * Logs an informational message.
     *
     * @param message The message to log.
     * @param args    Arguments for the message.
     */
    private void logInfo(String message, Object... args) {
        logger.info(message, args);
    }

    /**
     * Logs an error message.
     *
     * @param message The message to log.
     * @param args    Arguments for the message.
     */
    private void logError(String message, Object... args) {
        logger.error(message, args);
    }
}
