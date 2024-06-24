package com.example.final_project.controller;

import com.example.final_project.dto.UserDto;
import com.example.final_project.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/users")
public class UserController {
    private final UserService userService;


    @PostMapping(value = "/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto registertUser(@RequestBody UserDto userDto) {
        return userService.registerUser(userDto);
    }
}
