package com.example.final_project.dto.responsedDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryResponseDto {
    /**
     * Unique identifier for the category.
     */
    private long categoryId;

    /**
     * Name of the category.
     */
    private String name;
}
