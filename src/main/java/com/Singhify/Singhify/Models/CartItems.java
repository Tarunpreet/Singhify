package com.Singhify.Singhify.Models;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cart_items")

public class CartItems {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cartItemId;

    @ManyToOne
    @JoinColumn(name = "productId")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "cartId")
    private Cart cart;

    private Integer quantity;
    private double price=0.0;
    private double discount=0.0;
    private Boolean valid;

}
