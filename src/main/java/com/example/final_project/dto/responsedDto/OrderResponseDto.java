package com.example.final_project.dto.responsedDto;

import com.example.final_project.entity.enums.Delivery;
import com.example.final_project.entity.enums.Status;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderResponseDto {
    private Long orderId;
    private Timestamp createdAt;
    private String deliveryAddress;
    private String contactPhone;
    private Delivery deliveryMethod;
    private Status status;
    private Timestamp updatedAt;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("user")
    private UserResponseDto userResponseDto;
}
