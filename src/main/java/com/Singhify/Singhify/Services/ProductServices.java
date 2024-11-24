package com.Singhify.Singhify.Services;

import com.Singhify.Singhify.Data.DTO.ProductDTO;
import com.Singhify.Singhify.Data.PaginatedAPIResponse;
import com.Singhify.Singhify.Exception.EntityNotFoundException;
import com.Singhify.Singhify.Models.Category;
import com.Singhify.Singhify.Models.Product;
import com.Singhify.Singhify.Repos.CategoriesRepo;
import com.Singhify.Singhify.Repos.ProductRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServices {


    @Autowired
    ProductRepo productRepo;
    @Autowired
    ModelMapper mapper;
    @Autowired
    PaginatedAPIResponse<ProductDTO> paginatedProductResponse;
    @Autowired
    CategoriesRepo categoriesRepo;

    public void addProduct(Product product,int categoryId)
    {
        Category category=categoriesRepo.findById(categoryId).
                orElseThrow(()->new EntityNotFoundException("Category","id",categoryId));



    }
}
