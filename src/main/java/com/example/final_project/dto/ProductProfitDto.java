package com.example.final_project.dto;

import com.example.final_project.entity.query.ProductProfitInterface;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductProfitDto implements ProductProfitInterface {
    private String period;
    private BigDecimal sum;
}