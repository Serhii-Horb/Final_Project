package com.example.final_project.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    @JsonIgnore
    private Long userId;

    private String name;
    private String email;
    private String phoneNumber;
    private String passwordHash;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("Cart")
    private CartDto cartDto;
}
