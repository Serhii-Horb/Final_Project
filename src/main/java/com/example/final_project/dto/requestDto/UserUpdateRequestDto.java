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
    /**
     * User's full name for profile update.
     * Must be a non-empty string between 2 and 50 characters.
     *
     * @see #name
     */
    @NotBlank(message = "Invalid name: Empty name")
    // Ensures the name field is not empty. If empty, returns message: "Invalid name: Empty name".
    @Size(min = 2, max = 50, message = "Invalid Name: Must be of 2 - 50 characters")
    // Ensures the name length is between 2 and 50 characters. Error message for incorrect length: "Invalid Name: Must
    // be of 2 - 50 characters".
    private String name;

    /**
     * User's phone number for profile update.
     * Must be a non-empty string matching the international format, starting with '+' and followed by exactly 12 digits.
     *
     * @see #phone
     */
    @NotBlank(message = "Invalid Phone number: Empty number")
    // Ensures the phone field is not empty. If empty, returns message: "Invalid Phone number: Empty number".
    @Pattern(regexp = "^\\+(\\d{12})$", message = "Invalid phone number")
    // Validates phone number format. Must start with '+' followed by exactly 12 digits. Error message for invalid format:
    // "Invalid phone number".
    private String phone;
}
