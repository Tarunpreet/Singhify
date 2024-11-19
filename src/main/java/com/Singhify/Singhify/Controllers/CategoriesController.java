package com.Singhify.Singhify.Controllers;

import com.Singhify.Singhify.Constants.AppConstants;
import com.Singhify.Singhify.Data.APIResponse;
import com.Singhify.Singhify.Data.DTO.CategoryDTO;
import com.Singhify.Singhify.Services.CategoriesServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/categories")

public class CategoriesController {

    @Autowired
    CategoriesServices categoriesServices;
    private static final Logger logger = LoggerFactory.getLogger(CategoriesController.class);


    @PostMapping("addCategory")
    public ResponseEntity<String> addCategory(@RequestBody CategoryDTO categoryDTO)
    {
        logger.info("Received request to add category: " + categoryDTO);
       categoriesServices.addCategory( categoryDTO);
       return new ResponseEntity<>("Category Added", HttpStatus.CREATED);
    }
    @PutMapping("updateCategory/{id}")
    public ResponseEntity<CategoryDTO> updateCategory(@PathVariable int id, @RequestBody CategoryDTO categoryDTO)
    {
        logger.info("Received request to update category with id: " + id);
        CategoryDTO updatedCategoryDTO =categoriesServices.updateCategory(categoryDTO,id);
        return new ResponseEntity<>(updatedCategoryDTO, HttpStatus.OK);
    }

    @GetMapping("getCategories")
    public ResponseEntity<APIResponse<CategoryDTO>> getCategories(
            @RequestParam (name = "pageNumber",required = false,defaultValue = AppConstants.pageNumber) int pageNumber,
            @RequestParam (name = "pageSize",required = false,defaultValue = AppConstants.pageSize) int pageSize,
            @RequestParam (name = "sortType",required = false,defaultValue = "createdAt") String sortType,
            @RequestParam (name = "sortDir",required = false,defaultValue = AppConstants.pageDesc) String sortDir)
    {
        APIResponse<CategoryDTO> allCategories=categoriesServices.getCategories(pageNumber,pageSize,sortType,sortDir);
        return new ResponseEntity<>(allCategories, HttpStatus.OK);

    }
    @GetMapping("getCategory/{id}")
    public ResponseEntity<CategoryDTO> getCategory(@PathVariable int id)
    {
        CategoryDTO categoryDTO=categoriesServices.getCategory(id);
        return new ResponseEntity<>(categoryDTO, HttpStatus.OK);

    }
    @DeleteMapping("deleteCategory/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable int id)
    {
         categoriesServices.deleteCategory(id);
        return new ResponseEntity<>("Category Deleted..", HttpStatus.NO_CONTENT);

    }

}
