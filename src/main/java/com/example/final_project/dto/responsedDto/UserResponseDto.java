package com.example.final_project.dto.responsedDto;


import com.example.final_project.entity.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponseDto {
    /**
     * Unique identifier for the user.
     */
    private Long userID;

    /**
     * Name of the user.
     */
    private String name;

    /**
     * Email address of the user.
     */
    private String email;

    /**
     * Phone number of the user.
     */
    private String phoneNumber;

    /**
     * Hash of the user's password.
     * This field will be ignored in the JSON response.
     */
    @JsonIgnore
    private String passwordHash;

    /**
     * Refresh token of the user.
     * This field will be ignored in the JSON response.
     */
    @JsonIgnore
    private String refreshToken;

    /**
     * Role of the user.
     */
    private Role role;
}
