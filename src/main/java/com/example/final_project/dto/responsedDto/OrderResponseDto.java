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
    /**
     * Unique identifier for the order.
     */
    private Long orderId;

    /**
     * Timestamp indicating when the order was created.
     */
    private Timestamp createdAt;

    /**
     * Address where the order should be delivered.
     */
    private String deliveryAddress;

    /**
     * Contact phone number for the order.
     */
    private String contactPhone;

    /**
     * Delivery method for the order.
     */
    private Delivery deliveryMethod;

    /**
     * Current status of the order.
     */
    private Status status;

    /**
     * Timestamp indicating the last update time of the order.
     */
    private Timestamp updatedAt;

    /**
     * User details associated with the order.
     * This field will be included in the JSON response only if it is not null.
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("user")
    private UserResponseDto userResponseDto;
}
