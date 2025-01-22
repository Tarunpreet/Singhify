package com.Singhify.Singhify.Data.DTO;


import com.Singhify.Singhify.Models.Category;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class ProductDTO {

    private long id;

    private String productName;

    private int quantity=-1;


    private String productImage;

    private double price=0.0;

    private double discountperc=0.0;

    private double discountAmt;


    private int sellingPrice;

    private String description;

    private int categoryId;

    private LocalDateTime updatedAt;

    private String updatedBy;

    private LocalDateTime createdAt;

    private String createdBy;

}
