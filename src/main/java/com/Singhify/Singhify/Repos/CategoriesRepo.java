package com.Singhify.Singhify.Repos;

import com.Singhify.Singhify.Models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriesRepo  extends JpaRepository<Category,Integer> {
}
