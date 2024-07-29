package com.example.final_project.dto.requestDto;

import com.example.final_project.entity.enums.Delivery;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.criteria.Root;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderRequestDto {
    @JsonProperty("items")
    @NotEmpty(message = "Items list cannot be empty")
    private List<@Valid OrderItemRequestDto> orderItemsList = new ArrayList<>();

    @NotNull(message = "Delivery address cannot be empty!")
    @Size(min=5,max = 255, message = "The number of characters should not be less than 5 or exceed 255")
    @Pattern(regexp = "^[A-Za-z\\s]+\\d+$", message = "Invalid delivery address entered")
    private String deliveryAddress;

    @NotNull(message = "Delivery method cannot be empty!")
    private Delivery deliveryMethod;
}
