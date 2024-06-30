package com.example.final_project.dto.requestDto;

import jakarta.validation.constraints.Email;
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
public class UserRegisterRequestDto {
    @NotBlank(message = "Invalid name: Empty name")
    @Size(min = 2, max = 50, message = "Invalid Name: Must be of 2 - 50 characters")
    private String name;

    @NotBlank(message = "Invalid email: Empty email")
    @Email(message = "Invalid email")
    private String email;

    @NotBlank(message = "Invalid Phone number: Empty number")
    @Pattern(regexp = "^\\+(\\d{12})$", message = "Invalid phone number")
    private String phoneNumber;

    @NotBlank(message = "Invalid password number: Empty password")
    @Size(min = 8, max = 20, message = "Password must be between 8 and 20 characters")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$)",
            message = "Password must contain at least one digit, one lowercase letter, " +
                    "one uppercase letter, one special character, and no whitespace")
    private String passwordHash;
}
