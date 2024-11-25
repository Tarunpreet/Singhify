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
public interface ProductRepo extends JpaRepository<Product,Long> {

    Page<Product> findByCategory(Category category, Pageable pageDetails);

    @Query("select S from product where S.productName LIKE %:keyword%")
    Page<Product> findByKeyword(@Param("keyword")String keyword, Pageable pageDetails);
}
