package com.Singhify.Singhify.Services;

import com.Singhify.Singhify.Constants.AppConstants;
import com.Singhify.Singhify.Data.DTO.ProductDTO;
import com.Singhify.Singhify.APIResponses.PaginatedAPIResponse;
import com.Singhify.Singhify.Exception.EntityNotFoundException;
import com.Singhify.Singhify.Models.Category;
import com.Singhify.Singhify.Models.Product;
import com.Singhify.Singhify.Repos.CategoriesRepo;
import com.Singhify.Singhify.Repos.ProductRepo;
import com.Singhify.Singhify.Utilities.MappingData;
import com.Singhify.Singhify.Utilities.PaginationValid;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

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

    @Autowired
    CartService cartService;


    public ProductDTO addProduct(Product product, MultipartFile imageFile, int categoryId) throws IOException {
        Category category=categoriesRepo.findById(categoryId).
                orElseThrow(()->new EntityNotFoundException("Category","id",categoryId));


        product.setCategory(category);
        double discountAmt=0;
        if (product.getDiscountperc() != 0) {
            discountAmt = (double) (product.getPrice() * (product.getDiscountperc() / 100.0));
        }
        product.setDiscountAmt(discountAmt);
        product.setSellingPrice(product.getPrice()-product.getDiscountAmt());

        product.setCreatedAt(LocalDateTime.now());
        product.setProductImage("placeholder");
        Product savedProduct=productRepo.save(product);

        String imagePath="Products";
//                + File.separator+savedProduct.getId();
        String savedImageName=saveImage(imagePath,imageFile);
        product.setProductImage(savedImageName);

        savedProduct=productRepo.save(product);

        ProductDTO productDTO=new ProductDTO();
        mapper.map(savedProduct,productDTO);
        return productDTO;
    }

    private String saveImage(String imagePath, MultipartFile imageFile) throws IOException {
        String originalFileName = imageFile.getOriginalFilename();
        String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));

        String uniqueFileName = UUID.randomUUID() + fileExtension;


        String fullPath=AppConstants.imageRootDirPath +imagePath;
        System.out.println(fullPath);

        File imageDir=new File(fullPath);
        if(!imageDir.exists())
        {
            boolean created=imageDir.mkdir();
            if(!created)
            {
                throw  new IOException("Not able to create Image Dir ");
            }

        }
        File imageFileOnDisk = new File(imageDir,uniqueFileName);
        imageFile.transferTo(imageFileOnDisk);
        return uniqueFileName;

    }

    public PaginatedAPIResponse<ProductDTO> getAllProducts(int pageNumber,int pageSize
                                                         ,String sortType,String sortDir)
    {
        PaginationValid.checkParameters(pageSize,pageNumber);
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
        PaginationValid.checkParameters(pageSize,pageNumber);
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
                                                                 String sortDir, String keyword)
    {
        PaginationValid.checkParameters(pageSize,pageNumber);
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
    @Transactional
    public ProductDTO updateProduct(long productId, ProductDTO productDTO, MultipartFile imagefile) throws IOException {
        Product product=productRepo.findById(productId).
                orElseThrow(()->new EntityNotFoundException("Product","id",productId));
        // Update fields only if new values are provided
        if (productDTO.getProductName() != null) {
            product.setProductName(productDTO.getProductName());
        }
        if (productDTO.getDescription() != null) {
            product.setDescription(productDTO.getDescription());
        }
        if(productDTO.getPrice()!=0.0)
        {
            product.setPrice(productDTO.getPrice());

        }
        if(productDTO.getQuantity()!=-1)
        {
            product.setQuantity(productDTO.getQuantity());
        }
        String savedImage=product.getProductImage();
        if(imagefile!=null)
        {
            String imagePath="Products";
//                + File.separator+savedProduct.getId();
            String savedImageName=saveImage(imagePath,imagefile);
            product.setProductImage(savedImageName);
            String prevImage=AppConstants.imageRootDirPath+imagePath+File.separator+savedImage;
            File prevImagefile=new File(prevImage);
            prevImagefile.delete();
        }
        double discountAmt=0;
        //When price and Discount modified
        if (productDTO.getDiscountperc() != 0.0) {
            discountAmt = (double) (product.getPrice() * (productDTO.getDiscountperc() / 100.0));
            product.setDiscountperc(productDTO.getDiscountperc());
            product.setDiscountAmt(discountAmt);
        }
        //When price modified but disount not
        else {
            discountAmt = (double) (product.getPrice() * (product.getDiscountperc() / 100.0));
            product.setDiscountAmt(discountAmt);
        }
        product.setSellingPrice(product.getPrice()-product.getDiscountAmt());
        product.setUpdatedAt(LocalDateTime.now());
        Product updatedProduct=productRepo.save(product);


        cartService.updateProductInCart(updatedProduct);

        return mapper.map(updatedProduct,ProductDTO.class);
    }

    @Transactional
    public void deleteProduct(long productId) {
        Product product=productRepo.findById(productId).
                orElseThrow(()->new EntityNotFoundException("Product","id",productId));
        cartService.deleteProductFromCart(product);
        productRepo.delete(product);

    }

    public Product getProductById(Long productId) {
        return productRepo.findById(productId).
                orElseThrow(()->new EntityNotFoundException("Product","Id",productId));
    }
}

