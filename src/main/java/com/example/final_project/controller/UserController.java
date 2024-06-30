package com.example.final_project.controller;

import com.example.final_project.dto.requestDto.UserLoginRequestDto;
import com.example.final_project.dto.requestDto.UserRegisterRequestDto;
import com.example.final_project.dto.requestDto.UserUpdateRequestDto;
import com.example.final_project.dto.responsedDto.UserResponseDto;
import com.example.final_project.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/users")
public class UserController {
    private final UserService userService;

    @PostMapping(value = "/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponseDto registerUserProfile(@RequestBody UserRegisterRequestDto userRequestDto) {
        return userService.registerUserProfile(userRequestDto);
    }

    @PostMapping(value = "/login")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponseDto loginUserProfile(@RequestBody UserLoginRequestDto userLoginRequestDto) {
        return userService.loginUserProfile(userLoginRequestDto);
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserResponseDto updateUserProfile(@RequestBody UserUpdateRequestDto userUpdateRequestDto,
                                             @PathVariable @Valid @Min(1) Long id) {
        return userService.updateUserProfile(userUpdateRequestDto, id);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUserProfileById(@PathVariable @Valid @Min(1) Long id) {
        userService.deleteUserProfileById(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UserResponseDto> getAllUsersProfiles() {
        return userService.getAllUsers();
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserResponseDto getUserProfileById(@PathVariable @Valid @Min(1) Long id) {
        return userService.getUserById(id);
    }
}
