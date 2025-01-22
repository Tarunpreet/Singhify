package com.Singhify.Singhify.Data.DTO;


import com.Singhify.Singhify.APIResponses.PaginatedAPIResponse;
import com.Singhify.Singhify.Models.CartItems;
import com.Singhify.Singhify.Models.Product;
import com.Singhify.Singhify.Models.Users;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Data
@Component
public class CartDTO {


    private int cartId;
    private int userId;
    private PaginatedAPIResponse<CartItemDTO> cartItemDtos;
    private Double totalPrice=0.0;

}