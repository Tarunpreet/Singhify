package com.Singhify.Singhify.Models;

import com.Singhify.Singhify.Enum.PaymentMethod;
import com.Singhify.Singhify.Enum.PaymentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "orders")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long payment_id;

    @OneToOne(mappedBy = "payment",cascade = {CascadeType.MERGE,CascadeType.PERSIST})
    @JoinColumn
    private Orders order;

    private PaymentStatus paymentStatus;

    private Date payment_date;

    private String updated_by;

    private Date created_on;

    private Date updated_on;

    private double amount;

    private PaymentMethod paymentMethod;

    private String payment_reference_number;
}
