package com.example.final_project.dto;

import com.example.final_project.entity.query.ProductPendingInterface;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductPendingDto implements ProductPendingInterface {
    private Long productId;
    private String name;
    private Integer count;
    private String status;
}
