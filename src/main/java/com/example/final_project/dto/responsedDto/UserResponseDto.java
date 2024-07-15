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
    private Long userID;
    private String name;
    private String email;
    private String phoneNumber;
    @JsonIgnore
    private String passwordHash;
    @JsonIgnore
    private String refreshToken;
    private Role role;
}
