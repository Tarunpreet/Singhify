package com.Singhify.Singhify.Repos;

import com.Singhify.Singhify.Models.Cart;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepo extends JpaRepository<Cart,Integer> {
    Cart findByUser_UserId(int userId);

}
