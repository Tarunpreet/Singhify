package com.Singhify.Singhify.Data.DTO;

import com.Singhify.Singhify.Models.Orders;
import com.Singhify.Singhify.Models.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemsDTO {

    private long id;

    private long productId;

    private String productName;

    private double item_amount;

    private double tax_amount;

    private double total_amount;

    private Integer Quantity;
}
