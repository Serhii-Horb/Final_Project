package com.example.final_project.dto;

import com.example.final_project.entity.OrderItem;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDto {
    private long orderId;
    @NotBlank(message = "Invalid name: empty name")
    @Size(min=3,max = 20, message = "Invalid firstName: Must be of 3 - 20 characters")
    @Pattern(regexp = "^[A-Za-z]+[0-9]+$")
    private String deliveryAddress;

    @NotBlank(message = "Invalid name: empty name")
    @Size(min=3,max = 20, message = "Invalid firstName: Must be of 3 - 20 characters")
    private String deliveryMethod;

    private List<OrderItemDto> items = new ArrayList<>();
}
