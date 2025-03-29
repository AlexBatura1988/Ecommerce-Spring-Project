package com.ecommerce.project.service;

import com.ecommerce.project.exceptions.APIException;
import com.ecommerce.project.exceptions.ResourceNotFoundException;
import com.ecommerce.project.model.Category;
import com.ecommerce.project.repo.CategoryRepo;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepo categoryRepo;

    @Override
    public List<Category> getAllCategories() {
        List<Category> categories = categoryRepo.findAll();
        if (categories.isEmpty()) {
            throw new APIException("No categories created till now");
        }
        return categories;
    }

    @Override
    public void createCategory(Category category) {
        Category savedCategory = categoryRepo.findByCategoryName(category.getCategoryName());
        if(savedCategory != null)
            throw new APIException("Category with the name " +category.getCategoryName() + " already exist!!");
        categoryRepo.save(category);
    }

    @Override
    public Category updateCategory(Category category, Long categoryId) {

       Category savedCategory = categoryRepo.findById(categoryId)
               .orElseThrow(() -> new ResourceNotFoundException("Category","CategoryId",categoryId));
       category.setCategoryId(categoryId);
       savedCategory = categoryRepo.save(category);
       return savedCategory;
    }

    @Override
    public String deleteCategory(Long categoryId) {
        Category deletedCategory = categoryRepo.findById(categoryId)
                        .orElseThrow(() -> new ResourceNotFoundException("Category","CategoryId",categoryId));
        categoryRepo.delete(deletedCategory);

        return "Category with categoryId: " + categoryId + " successfully deleted from DB";

    }

}
