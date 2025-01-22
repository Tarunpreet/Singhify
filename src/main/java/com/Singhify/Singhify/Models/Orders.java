package com.Singhify.Singhify.Models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "orders")
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long order_id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users users;

    @OneToMany(mappedBy = "order")
    private List<OrderItems> orderItems;


    private LocalDateTime order_date;

    private double amount;

    private double tax_amount;


    private double total_amount;

    private double final_amount;

    @OneToOne
    @JoinColumn(name = "payment_id")
    private Payment payment;

    private LocalDateTime created_on;

    private LocalDateTime updated_on;

    private String updated_by;

    private  double discountAmt;



}
