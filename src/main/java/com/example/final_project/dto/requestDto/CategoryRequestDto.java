package com.example.final_project.dto.requestDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryRequestDto {
    @NotBlank(message = "Invalid name: Empty name")
    @Size(min = 2, max = 100, message = "Invalid category name: Must be of 2 - 100 characters")
    private String name;
}
