package com.Singhify.Singhify.Controllers;

import com.Singhify.Singhify.Constants.AppConstants;
import com.Singhify.Singhify.Data.DTO.ProductDTO;
import com.Singhify.Singhify.APIResponses.PaginatedAPIResponse;
import com.Singhify.Singhify.Models.Product;
import com.Singhify.Singhify.Services.ProductServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/api")
public class ProductsController {

    @Autowired
    private ProductServices productServices;

    @PostMapping("/admin/{categoryId}/addProduct")
    public ResponseEntity<ProductDTO> addProduct(@RequestPart Product product
                                                 , @RequestPart MultipartFile imagefile,
                                             @PathVariable int categoryId) throws IOException {
        ProductDTO productDTO=productServices.addProduct(product, imagefile,categoryId);
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
    @GetMapping("/public/Products")
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
    @PutMapping("/admin/product/{productId}/update")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable long productId,@RequestPart ProductDTO productDTO,@RequestPart(required = false) MultipartFile imagefile) throws IOException {
        ProductDTO updatedDTO=productServices.updateProduct(productId,productDTO,imagefile);
        return new ResponseEntity<>(updatedDTO, HttpStatus.OK);
    }
   @DeleteMapping("/admin/product/{productId}/delete")
   public ResponseEntity<String> deleteProduct(@PathVariable long productId)
   {
       productServices.deleteProduct(productId);
       return new ResponseEntity<>("Product Deleted", HttpStatus.NO_CONTENT);
   }

}
