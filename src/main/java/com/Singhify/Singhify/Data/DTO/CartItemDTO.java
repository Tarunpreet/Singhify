package com.Singhify.Singhify.Data.DTO;

import com.Singhify.Singhify.Models.Cart;
import com.Singhify.Singhify.Models.Product;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@Component
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class CartItemDTO
{
    private int cartItemId;

    private long productId;

    @JsonManagedReference
    private Product product;

    private int cartId;

    private Integer quantity;

    private double price=0.0;

    private double discount=0.0;

    private Boolean valid;
}
