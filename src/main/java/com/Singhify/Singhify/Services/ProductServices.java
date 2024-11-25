package com.Singhify.Singhify.Services;

import com.Singhify.Singhify.Constants.AppConstants;
import com.Singhify.Singhify.Data.DTO.ProductDTO;
import com.Singhify.Singhify.Data.PaginatedAPIResponse;
import com.Singhify.Singhify.Exception.EntityNotFoundException;
import com.Singhify.Singhify.Models.Category;
import com.Singhify.Singhify.Models.Product;
import com.Singhify.Singhify.Repos.CategoriesRepo;
import com.Singhify.Singhify.Repos.ProductRepo;
import com.Singhify.Singhify.Utilities.MappingData;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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
    @Autowired
    MappingData<Product,ProductDTO> mappingProductData;


    public ProductDTO addProduct(Product product,int categoryId)
    {
        Category category=categoriesRepo.findById(categoryId).
                orElseThrow(()->new EntityNotFoundException("Category","id",categoryId));

        product.setCategory(category);
        double discountAmt=0;
        if (product.getDiscountperc() != 0) {
            discountAmt = (double) (product.getPrice() * (product.getDiscountperc() / 100.0));
        }
        product.setDiscountAmt(discountAmt);
        product.setSellingPrice(product.getPrice()-product.getDiscountAmt());
        product.setProductImage("iMac.png");
        product.setCreatedAt(LocalDateTime.now());
        Product savedProduct=productRepo.save(product);
        ProductDTO productDTO=new ProductDTO();
        mapper.map(savedProduct,productDTO);
        return productDTO;
    }

    public PaginatedAPIResponse<ProductDTO> getAllProducts(int pageNumber,int pageSize
                                                         ,String sortType,String sortDir)
    {
        Sort sort=sortDir.equalsIgnoreCase(AppConstants.pageAsc)?Sort.by(sortType).ascending():Sort.by(sortType).descending();
        Pageable pageDetails= PageRequest.of(pageNumber,pageSize,sort);

        Page<Product> productsPage=productRepo.findAll(pageDetails);
        List<Product> products=productsPage.getContent();
        if(products.isEmpty())
        {
            throw new EntityNotFoundException("Product");
        }
        List<ProductDTO> productsDTO=products.stream().
                map(product -> mapper.map(product, ProductDTO.class)).toList();

        paginatedProductResponse.setContent(productsDTO);
        return mappingProductData.mappingPageMetaData(productsPage,paginatedProductResponse);

    }
    public PaginatedAPIResponse<ProductDTO> getProductsByCategory(int pageNumber,int pageSize
            ,String sortType,String sortDir,int categoryId)
    {
        Category category=categoriesRepo.findById(categoryId).
                orElseThrow(()->new EntityNotFoundException("Category","id",categoryId));

        Sort sort=sortDir.equalsIgnoreCase(AppConstants.pageAsc)?Sort.by(sortType).ascending():Sort.by(sortType).descending();
        Pageable pageDetails= PageRequest.of(pageNumber,pageSize,sort);

        Page<Product> productsPage=productRepo.findByCategory(category,pageDetails);

        List<Product> products=productsPage.getContent();
        if(products.isEmpty())
        {
            throw new EntityNotFoundException("Product");
        }
        List<ProductDTO> productsDTO=products.stream().
                map(product -> mapper.map(product, ProductDTO.class)).toList();

        paginatedProductResponse.setContent(productsDTO);
        return mappingProductData.mappingPageMetaData(productsPage,paginatedProductResponse);

    }

    public PaginatedAPIResponse<ProductDTO> getProductsByKeyword(int pageNumber, int pageSize, String sortType,
                                                                 String sortDir, String keyword) {

        Sort sort=sortDir.equalsIgnoreCase(AppConstants.pageAsc)?Sort.by(sortType).ascending():Sort.by(sortType).descending();
        Pageable pageDetails= PageRequest.of(pageNumber,pageSize,sort);

        Page<Product> productsPage=productRepo.findByKeyword(keyword,pageDetails);
        List<Product> products=productsPage.getContent();
        if(products.isEmpty())
        {
            throw  new EntityNotFoundException("Products","Keyword",keyword);
        }
        List<ProductDTO> productsDTO=products.stream().
                map(product -> mapper.map(product, ProductDTO.class)).toList();

        paginatedProductResponse.setContent(productsDTO);
        return mappingProductData.mappingPageMetaData(productsPage,paginatedProductResponse);

    }
}
