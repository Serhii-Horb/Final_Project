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
public class UserLoginRequestDto {
    /**
     * User's email address for authentication.
     * Must be a non-empty string and a valid email format.
     *
     * @see #email
     */
    @NotBlank(message = "Invalid email: Empty email")
    // Ensures the email field is not empty. If empty, returns message: "Invalid email: Empty email".
    @Email(message = "Invalid email")
    // Ensures the email field contains a valid email address format. Error message for invalid email: "Invalid email".
    private String email;

    /**
     * User's password for authentication.
     * Must be a non-empty string, between 8 and 20 characters long,
     * and meet complexity requirements (at least one digit, one lowercase letter,
     * one uppercase letter, one special character, and no whitespace).
     *
     * @see #password
     */
    @NotBlank(message = "Invalid password number: Empty password")
    // Ensures the password field is not empty. If empty, returns message: "Invalid password number: Empty password".
    @Size(min = 8, max = 20, message = "Password must be between 8 and 20 characters")
    // Ensures password length is between 8 and 20 characters. Error message for incorrect length: "Password must be between 8 and 20 characters".
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$)",
            message = "Password must contain at least one digit, one lowercase letter, " +
                    "one uppercase letter, one special character, and no whitespace")
    // Ensures password contains at least one digit, one lowercase letter, one uppercase letter, one special character, and no whitespace. Error message for non-compliance: "Password must contain at least one digit, one lowercase letter, one uppercase letter, one special character, and no whitespace".
    private String password;
}
