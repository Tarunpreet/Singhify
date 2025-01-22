package com.Singhify.Singhify.Data.DTO;

import com.Singhify.Singhify.Models.OrderItems;
import com.Singhify.Singhify.Models.Payment;
import com.Singhify.Singhify.Models.Users;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrdersDTO {

    private long order_id;


    private long userId;


    private List<OrderItemsDTO> orderItemsDTOS;

    private LocalDateTime order_date;

    private double amount;

    private double tax_amount;

   //tax+amount
    private double total_amount;

    //total amount - discount
    private double final_amount;

    private long paymentId;

    private LocalDateTime created_on;

    private LocalDateTime updated_on;

    private String updated_by;

    private  double discountAmt;
}
