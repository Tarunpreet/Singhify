package com.Singhify.Singhify.Data.DTO;


import com.Singhify.Singhify.Models.Category;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {

    private long id;

    private String productName;

    private int quantity;

    private String image;

    private int price;

    private int discountAmt;

    private  int discountperc;

    private int sellingPrice;

    private String description;

    private int categoryId;

    private LocalDateTime updatedAt;

    private String updatedBy;

    private LocalDateTime createdAt;

    private String createdBy;

}
