package com.example.final_project.dto.requestDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserUpdateRequestDto {
    @NotBlank(message = "Invalid name: Empty name")
    @Size(min = 2, max = 50, message = "Invalid Name: Must be of 2 - 50 characters")
    private String name;

    @NotBlank(message = "Invalid Phone number: Empty number")
    @Pattern(regexp = "^\\+(\\d{12})$", message = "Invalid phone number")
    private String phone;
}
