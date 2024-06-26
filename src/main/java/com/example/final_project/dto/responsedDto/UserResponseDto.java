package com.example.final_project.dto.responsedDto;


import com.example.final_project.entity.enums.Role;
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
    private String password;
    private Role role;
}
