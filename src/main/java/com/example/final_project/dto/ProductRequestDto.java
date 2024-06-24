package com.example.final_project.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductRequestDto {

    private String name;
    private String description;
    private BigDecimal price;
    private BigDecimal discountPrice;
    private String imageURL;
    private Long CategoryId;

}
