package com.example.final_project.controller;

import com.example.final_project.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/users")
public class UserController {
//    private final UserService userService;
//
//    @PostMapping(value = "/register")
//    @ResponseStatus(HttpStatus.CREATED)
//    public UserDto registerUserProfile(@RequestBody UserDto userDto) {
//        return userService.registerUserProfile(userDto);
//    }
//
//    @PostMapping(value = "/login")
//    @ResponseStatus(HttpStatus.CREATED)
//    public UserDto loginUserProfile(@RequestBody UserDto userDto) {
//        return userService.loginUserProfile(userDto);
//    }
//
//    @PutMapping
//    @ResponseStatus(HttpStatus.OK)
//    public UserDto updateUserProfile(@RequestBody UserDto usersDto) {
//        return userService.updateUserProfile(usersDto);
//    }
//
//    @DeleteMapping(value = "/{id}")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    public void deleteUserProfileById(@PathVariable Long id) {
//        userService.deleteUserProfileById(id);
//    }

}
