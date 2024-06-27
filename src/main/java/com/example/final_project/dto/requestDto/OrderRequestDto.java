package com.example.final_project.dto.requestDto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderRequestDto {
    @JsonProperty("items")
    @NotEmpty(message = "Items list cannot be empty")
    private Set<@Valid OrderItemRequestDto> orderItemsSet;

    @NotBlank(message = "Invalid address: Empty address")
    @Size(max = 255, message = "The number of characters should not exceed 255")
    @Pattern(regexp = "^[a-zA-Z0-9.,\\s]+$", message = "Invalid delivery address entered")
    private String deliveryAddress;

    @NotBlank(message = "Invalid delivery: Empty delivery")
    @Size(min = 3, max = 30, message = "Invalid Delivery method: Must be one of: COURIER_DELIVERY, SELF_DELIVERY " +
            "or DEPARTMENT_DELIVERY")
    private String deliveryMethod;
}
