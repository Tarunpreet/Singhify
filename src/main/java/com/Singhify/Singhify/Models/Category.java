package com.Singhify.Singhify.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false,updatable = false)
    private LocalDateTime createdAt;
    @Column
    private String description;
    @Column(nullable = false)
    private String createdBy;
    @Column
    private LocalDateTime updatedAt;
    @Column
    private String updatedBy;


}
