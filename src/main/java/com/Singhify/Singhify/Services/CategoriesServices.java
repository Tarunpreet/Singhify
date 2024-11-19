package com.Singhify.Singhify.Services;

import com.Singhify.Singhify.Constants.AppConstants;
import com.Singhify.Singhify.Controllers.CategoriesController;
import com.Singhify.Singhify.Data.APIResponse;
import com.Singhify.Singhify.Data.DTO.CategoryDTO;
import com.Singhify.Singhify.Exception.EntityNotFoundException;
import com.Singhify.Singhify.Models.Category;
import com.Singhify.Singhify.Repos.CategoriesRepo;
import com.Singhify.Singhify.Utilities.MappingData;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CategoriesServices {

    @Autowired
    CategoriesRepo categoriesRepo;
    @Autowired
    ModelMapper mapper;
    @Autowired
    APIResponse<CategoryDTO> categoryResponse;
    @Autowired
    MappingData<Category,CategoryDTO> mapData;

    private static final Logger logger = LoggerFactory.getLogger(CategoriesController.class);


    public void addCategory(CategoryDTO categoryDTO)
    {
        categoryDTO.setCreatedAt(LocalDateTime.now());
        Category category=mapper.map(categoryDTO, Category.class);
        categoriesRepo.save(category);
    }

    public CategoryDTO updateCategory(CategoryDTO categoryDTO, int id) {
        Category existingCategory=categoriesRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category","id",id));

        // Update fields only if new values are provided
        if (categoryDTO.getName() != null) {
            existingCategory.setName(categoryDTO.getName());
        }
        if (categoryDTO.getDescription() != null) {
            existingCategory.setDescription(categoryDTO.getDescription());
        }

        // Preserve createdBy and update updatedAt
        existingCategory.setCreatedBy(existingCategory.getCreatedBy());
        existingCategory.setUpdatedAt(LocalDateTime.now());

        // Save the updated entity
        categoriesRepo.save(existingCategory);

        //   map and return the updated dto
        return mapper.map(existingCategory, CategoryDTO.class);
    }

    public APIResponse<CategoryDTO> getCategories(int pageNumber, int pageSize, String sortType, String sortDir){

        Sort sort=sortDir.equalsIgnoreCase(AppConstants.pageAsc)?Sort.by(sortType).ascending():Sort.by(sortType).descending();
        Pageable pageDetails= PageRequest.of(pageNumber,pageSize,sort);

        Page<Category> categoryPage=  categoriesRepo.findAll(pageDetails);
       List<Category> categories=categoryPage.getContent();
       if(categories.isEmpty())
       {
          throw new EntityNotFoundException("Categories");
       }
       System.out.println(categories);

        List<CategoryDTO> categoriesDto=categories.stream()
                .map(category -> mapper.map(category, CategoryDTO.class)).toList();
        categoryResponse.setContent(categoriesDto);

        return mapData.mappingPageMetaData(categoryPage,categoryResponse);
    }

    public CategoryDTO getCategory(int id) {
        Category category=categoriesRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category","id",id));

        return mapper.map(category, CategoryDTO.class);
    }

    public void deleteCategory(int id) {
        Category category=categoriesRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category","id",id));
        categoriesRepo.delete(category);
    }


}
