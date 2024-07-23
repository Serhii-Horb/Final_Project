package com.example.final_project.controller;

import com.example.final_project.dto.requestDto.UserUpdateRequestDto;
import com.example.final_project.dto.responsedDto.UserResponseDto;
import com.example.final_project.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/users")
@Tag(name = "User controller.", description = "Getting a user, deleting or updating a user profile is done using this controller.")
public class UserController {
    private final UserService userService;

    /**
     * Endpoint to update a user's profile.
     *
     * @param userUpdateRequestDto DTO containing the data to update the user's profile.
     * @param userId               the ID of the user whose profile is being updated.
     * @return a DTO containing the updated user profile.
     */
    @PutMapping(value = "/{userId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "User update.",
            description = "Allows the user to update their data."
    )
    public UserResponseDto updateUserProfile(@RequestBody @Valid UserUpdateRequestDto userUpdateRequestDto,
                                             @PathVariable @Valid @Min(1) Long userId) {
        return userService.updateUserProfile(userUpdateRequestDto, userId);
    }

    /**
     * Endpoint to delete a user profile.
     *
     * @param userId the ID of the user whose profile is to be deleted.
     */
    @DeleteMapping(value = "/{userId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "Deletes the user.",
            description = "Allows you to delete a user profile."
    )
    public void deleteUserProfileById(@PathVariable @Valid @Min(1) Long userId) {
        userService.deleteUserProfileById(userId);
    }

    /**
     * Endpoint to retrieve all user profiles.
     *
     * @return a list of DTOs containing all registered users' profiles.
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "Finds all users.",
            description = "Allows you to find all registered users."
    )
    public List<UserResponseDto> getAllUsersProfiles() {
        return userService.getAllUsers();
    }

    /**
     * Endpoint to retrieve a user profile by ID.
     *
     * @param userId the ID of the user whose profile is being retrieved.
     * @return a DTO containing the user's profile.
     */
    @GetMapping(value = "/{userId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "Finds the user.",
            description = "Allows you to find one of the registered users by using the id."
    )
    public UserResponseDto getUserProfileById(@PathVariable @Valid @Min(1) Long userId) {
        return userService.getUserById(userId);
    }
}
