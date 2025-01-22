package com.Singhify.Singhify.Enum;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;

public enum PaymentMethod {

        CREDIT_CARD,
        DEBIT_CARD,
        NET_BANKING,
        UPI,                   // Unified Payments Interface
        CASH_ON_DELIVERY,      // COD
        EMI,                   // Equated Monthly Installments

}

