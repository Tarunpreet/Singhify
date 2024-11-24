package com.Singhify.Singhify.Models;


import jakarta.annotation.ManagedBean;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Product {

    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String productName;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private int quantity;

    @Column
    private int discountAmt;

    @Column
    private  int discountperc;

    @Column(nullable = false)
    private int sellingPrice;

    @Column
    private String description;

    @ManyToOne
    @JoinColumn(name = "categoryId")
    private Category category;

    @Column
    private LocalDateTime updatedAt;

    @Column
    private String updatedBy;

    @Column(nullable = false,updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false,updatable = false)
    private String createdBy;



}
