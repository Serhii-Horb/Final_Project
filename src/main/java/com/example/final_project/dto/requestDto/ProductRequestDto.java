package com.example.final_project.dto.requestDto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductRequestDto {
    @NotBlank(message = "Invalid name: Empty name")
    private String name;

    @NotBlank(message = "Invalid description: Empty description")
    private String description;

    @DecimalMin(value = "0.0")
    @Digits(integer = 5, fraction = 3)
    private BigDecimal price;

    @DecimalMin(value = "0.0")
    @Digits(integer = 4, fraction = 2)
    private BigDecimal discountPrice;

    @NotBlank(message = "Invalid image: Empty imageURL")
    @Pattern(regexp = "^(https?|ftp)://.*$", message = "Invalid URL")
    private String imageURL;

    private Long categoryId;
}
