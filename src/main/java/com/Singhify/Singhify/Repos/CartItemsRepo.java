package com.Singhify.Singhify.Repos;

import com.Singhify.Singhify.Models.CartItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemsRepo  extends JpaRepository<CartItems,Integer> {
    CartItems findByProduct_Id(long productId);
}
