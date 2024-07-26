package com.example.final_project.dto.responsedDto;

import com.example.final_project.entity.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatusResponseDto {
    /**
     * Status display.
     */
    private Status status;
}
