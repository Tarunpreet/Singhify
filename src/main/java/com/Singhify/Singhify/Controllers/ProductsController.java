package com.Singhify.Singhify.Controllers;

import com.Singhify.Singhify.Data.DTO.ProductDTO;
import com.Singhify.Singhify.Models.Product;
import com.Singhify.Singhify.Services.ProductServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/api")
public class ProductsController {

    @Autowired
    private ProductServices productServices;

    @PostMapping("/admin/{categoryId}/addProduct")
    public ResponseEntity<String> addProduct(@RequestBody Product product
                                            ,@RequestParam int categoryId)
    {
        productServices.addProduct(product,categoryId);
    }

}
