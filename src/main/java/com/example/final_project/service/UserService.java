package com.example.final_project.service;

import com.example.final_project.entity.User;
import com.example.final_project.mapper.Mappers;
import com.example.final_project.repository.CartRepository;
import com.example.final_project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final Mappers mappers;
//
//    public UserDto registerUserProfile(UserDto userDto) {
//        User user = mappers.convertToUser(userDto);
//
//        user.setUserId(0L);
//        User savedUser = userRepository.save(user);
//
//        return mappers.convertToUserDto(savedUser);
//    }
//
//    public UserDto loginUserProfile(UserDto userDto) {
//        return null;
//    }
//
//    public UserDto updateUserProfile(UserDto userDto) {
//        if (userDto.getUserId() <= 0) { // При редактировании такого быть не должно, нужно вывести пользователю ошибку
//            return null;
//        }
//
//        User user = userRepository.findById(userDto.getUserId()).orElse(null);
//        if (user == null) {// Объект в БД не найден с таким Id, нужно вывести пользователю ошибку
//            return null;
//        }
//
//        if (userDto.getUserId() != user.getUserId()) {//номер users, введенный пользователем не совпадает с тем, который прописан в базе данных
//            return null;
//        }
//
//        user = mappers.convertToUser(userDto);
//        User updatedUser = userRepository.save(user);
//        UserDto responseUserDto = mappers.convertToUserDto(updatedUser);
//
//        return responseUserDto;
//
//    }
//
//    public void deleteUserProfileById(Long id) {
//        User user = userRepository.findById(id).orElse(null);
//        if (user != null) {
//            userRepository.delete(user);
//        }
//    }

}
