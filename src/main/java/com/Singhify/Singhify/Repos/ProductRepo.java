package com.Singhify.Singhify.Repos;

import com.Singhify.Singhify.Models.Category;
import com.Singhify.Singhify.Models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {

    // Method to find products by category with pagination
    Page<Product> findByCategory(Category category, Pageable pageDetails);

    // Method for keyword-based search with pagination
    @Query("SELECT p FROM Product p WHERE p.productName LIKE %:keyword%")
    Page<Product> findByKeyword(@Param("keyword") String keyword, Pageable pageDetails);
}
