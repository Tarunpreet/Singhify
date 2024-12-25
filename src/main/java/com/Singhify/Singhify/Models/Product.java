package com.Singhify.Singhify.Models;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.annotation.ManagedBean;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;
import org.apache.catalina.User;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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


    private String productImage;

    @Column(nullable = false)
    @Min(0)
    private double price;

    @Column(nullable = false)
    private int quantity;

    @Column
    private double discountAmt;

    @Column
    @Min(0)
    @Max(100)
    private  double discountperc=0;

    @Column(nullable = false)
    private  double sellingPrice;

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

    @ManyToOne()
    @JoinColumn(name="seller_id")
    @JsonBackReference
    private Users user;

    @OneToMany(mappedBy = "product")
    @ToString.Exclude
    @JsonBackReference
   private List<CartItems> cartItems=new ArrayList<>();

}
