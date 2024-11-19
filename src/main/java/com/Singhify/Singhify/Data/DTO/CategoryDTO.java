package com.Singhify.Singhify.Data.DTO;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO {

    private int id;

     private String name;

    private LocalDateTime createdAt;

    private String description;

    private String createdBy;

    private LocalDateTime updatedAt;

    private String updatedBy;
}
