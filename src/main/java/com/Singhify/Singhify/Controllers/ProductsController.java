package com.Singhify.Singhify.Controllers;

import com.Singhify.Singhify.Constants.AppConstants;
import com.Singhify.Singhify.Data.DTO.CategoryDTO;
import com.Singhify.Singhify.Data.DTO.ProductDTO;
import com.Singhify.Singhify.Data.PaginatedAPIResponse;
import com.Singhify.Singhify.Models.Product;
import com.Singhify.Singhify.Services.ProductServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api")
public class ProductsController {

    @Autowired
    private ProductServices productServices;

    @PostMapping("/admin/{categoryId}/addProduct")
    public ResponseEntity<ProductDTO> addProduct(@RequestBody Product product
                                            ,@PathVariable int categoryId)
    {
        ProductDTO productDTO=productServices.addProduct(product,categoryId);
        return new ResponseEntity<>(productDTO, HttpStatus.CREATED);
    }
    @GetMapping("/public/getProducts")
    public ResponseEntity<PaginatedAPIResponse<ProductDTO>> getProducts(
            @RequestParam (name = "pageNumber",required = false,defaultValue = AppConstants.pageNumber) int pageNumber,
            @RequestParam (name = "pageSize",required = false,defaultValue = AppConstants.pageSize) int pageSize,
            @RequestParam (name = "sortType",required = false,defaultValue = "createdAt") String sortType,
            @RequestParam (name = "sortDir",required = false,defaultValue = AppConstants.pageDesc) String sortDir)
    {
        PaginatedAPIResponse<ProductDTO> allProducts=productServices.getAllProducts(pageNumber,pageSize,sortType,sortDir);
        return new ResponseEntity<>(allProducts, HttpStatus.OK);

    }
    @GetMapping("/public/category/{categoryId}/getProducts")
    public ResponseEntity<PaginatedAPIResponse<ProductDTO>> getProductsByCategory(
            @PathVariable int categoryId,
            @RequestParam (name = "pageNumber",required = false,defaultValue = AppConstants.pageNumber) int pageNumber,
            @RequestParam (name = "pageSize",required = false,defaultValue = AppConstants.pageSize) int pageSize,
            @RequestParam (name = "sortType",required = false,defaultValue = "createdAt") String sortType,
            @RequestParam (name = "sortDir",required = false,defaultValue = AppConstants.pageDesc) String sortDir)
    {
        PaginatedAPIResponse<ProductDTO> allProducts=productServices.getProductsByCategory(pageNumber,pageSize,sortType,sortDir,categoryId);
        return new ResponseEntity<>(allProducts, HttpStatus.OK);

    }
    @GetMapping("/public/products")
    public ResponseEntity<PaginatedAPIResponse<ProductDTO>> getProductsByKeyword(
            @RequestParam String keyword,
            @RequestParam (name = "pageNumber",required = false,defaultValue = AppConstants.pageNumber) int pageNumber,
            @RequestParam (name = "pageSize",required = false,defaultValue = AppConstants.pageSize) int pageSize,
            @RequestParam (name = "sortType",required = false,defaultValue = "createdAt") String sortType,
            @RequestParam (name = "sortDir",required = false,defaultValue = AppConstants.pageDesc) String sortDir)
    {
        PaginatedAPIResponse<ProductDTO> allProducts=productServices.getProductsByKeyword(pageNumber,pageSize,sortType,sortDir,keyword);
        return new ResponseEntity<>(allProducts, HttpStatus.FOUND);

    }


}
