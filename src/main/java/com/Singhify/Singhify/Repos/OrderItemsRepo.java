package com.Singhify.Singhify.Repos;

import com.Singhify.Singhify.Models.OrderItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemsRepo extends JpaRepository<OrderItems,Long> {

}
