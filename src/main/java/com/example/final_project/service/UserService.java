package com.example.final_project.service;

import com.example.final_project.config.MapperUtil;
import com.example.final_project.dto.requestDto.UserLoginRequestDto;
import com.example.final_project.dto.requestDto.UserRegisterRequestDto;
import com.example.final_project.dto.requestDto.UserUpdateRequestDto;
import com.example.final_project.dto.responsedDto.UserResponseDto;
import com.example.final_project.entity.Cart;
import com.example.final_project.entity.User;
import com.example.final_project.entity.enums.Role;
import com.example.final_project.exceptions.AuthorizationException;
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
        // Проверка на существование пользователя с таким же email или phone
        if (userRepository.existsByEmail(userRequestDto.getEmail()) ||
                userRepository.existsByPhoneNumber(userRequestDto.getPhoneNumber())) {
            logger.error("User with email {} and phone {} already exists", userRequestDto.getEmail(), userRequestDto.getPhoneNumber());
            throw new AuthorizationException("User already exists");
        }

        // Преобразование DTO в сущность User
        User user = mappers.convertToUser(userRequestDto);

        // Установка значения userId в 0, чтобы Hibernate создавал нового пользователя
        // Установка роли пользователя
        user.setRole(Role.USER);
        Cart cart = new Cart();
        cart.setUser(user);
        user.setCart(cart);

        // Сохранение пользователя в базе данных
        User savedUser;
        try {
            savedUser = userRepository.save(user);
        } catch (Exception exception) {
         //   logger.error("Error saving user", exception);
            throw new AuthorizationException("Error saving user");
        }

        // Преобразование сохраненной сущности User в DTO
        return mappers.convertToUserResponseDto(savedUser);
    }

    public UserResponseDto loginUserProfile(UserLoginRequestDto userLoginRequestDto) {
        if (!userRepository.existsByEmail(userLoginRequestDto.getEmail())) {
            logger.error("User with email {} not exists", userLoginRequestDto.getEmail());
            throw new AuthorizationException("User not found");
        }

        User user = userRepository.findByEmailAndPassword (userLoginRequestDto.getEmail(),
                userLoginRequestDto.getPassword())
                .orElseThrow(()-> new AuthorizationException("Incorrect password. Please try again"));

        return mappers.convertToUserResponseDto(user);
    }

    public UserResponseDto updateUserProfile(UserUpdateRequestDto userUpdateRequestDto, Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundInDbException("Incorrect id in DB."));
        user.setName(userUpdateRequestDto.getName());
        user.setPhoneNumber(userUpdateRequestDto.getPhone());

        // Сохранение пользователя в базе данных
        User savedUser;
        try {
            savedUser = userRepository.save(user);
        } catch (Exception exception) {
            logger.error("Error saving user", exception);
            throw new NotFoundInDbException("Error saving user");
        }

        return mappers.convertToUserResponseDto(savedUser);
    }

    public void deleteUserProfileById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundInDbException("Incorrect id of user."));
        userRepository.delete(user);
    }

    public List<UserResponseDto> getAllUsers() {
        List<User> usersList = userRepository.findAll();
        return MapperUtil.convertList(usersList, mappers::convertToUserResponseDto);
    }

    public UserResponseDto getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundInDbException("Incorrect id of user."));

        UserResponseDto userResponseDto = mappers.convertToUserResponseDto(user);
        logger.info("User with ID {} successfully fetched", id);
        return userResponseDto;
    }
}
