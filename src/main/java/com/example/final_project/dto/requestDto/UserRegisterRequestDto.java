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
    /**
     * User's full name for registration.
     * Must be a non-empty string between 2 and 50 characters.
     *
     * @see #name
     */
    @NotBlank(message = "Invalid name: Empty name")
    // Ensures the name field is not empty. If empty, returns message: "Invalid name: Empty name".
    @Size(min = 2, max = 50, message = "Invalid Name: Must be of 2 - 50 characters")
    // Ensures the name length is between 2 and 50 characters. Error message for incorrect length: "Invalid Name:
    // Must be of 2 - 50 characters".
    private String name;

    /**
     * User's email address for registration.
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
     * User's phone number for registration.
     * Must be a non-empty string matching the international format, starting with '+' and followed by exactly 12 digits.
     *
     * @see #phoneNumber
     */
    @NotBlank(message = "Invalid Phone number: Empty number")
    // Ensures the phoneNumber field is not empty. If empty, returns message: "Invalid Phone number: Empty number".
    @Pattern(regexp = "^\\+(\\d{12})$", message = "Invalid phone number")
    // Validates phone number format. Must start with '+' followed by exactly 12 digits. Error message for invalid format:
    // "Invalid phone number".
    private String phoneNumber;

    /**
     * User's password for registration.
     * Must be a non-empty string between 8 and 20 characters long,
     * and meet complexity requirements (at least one digit, one lowercase letter,
     * one uppercase letter, one special character, and no whitespace).
     *
     * @see #passwordHash
     */
    @NotBlank(message = "Invalid password number: Empty password")
    // Ensures the password field is not empty. If empty, returns message: "Invalid password number: Empty password".
    @Size(min = 8, max = 20, message = "Password must be between 8 and 20 characters")
    // Ensures password length is between 8 and 20 characters. Error message for incorrect length: "Password must be
    // between 8 and 20 characters".
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$)",
            message = "Password must contain at least one digit, one lowercase letter, " +
                    "one uppercase letter, one special character, and no whitespace")
    // Ensures password contains at least one digit, one lowercase letter, one uppercase letter, one special character,
    // and no whitespace. Error message for non-compliance: "Password must contain at least one digit, one lowercase letter,
    // one uppercase letter, one special character, and no whitespace".
    private String passwordHash;
}
