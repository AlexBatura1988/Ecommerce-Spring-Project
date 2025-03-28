package com.ecommerce.project.service;

import com.ecommerce.project.model.Category;
import com.ecommerce.project.repo.CategoryRepo;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepo categoryRepo;

    @Override
    public List<Category> getAllCategories() {
        return categoryRepo.findAll();
    }

    @Override
    public void createCategory(Category category) {
        categoryRepo.save(category);
    }

    @Override
    public Category updateCategory(Category category, Long categoryId) {

       Category savedCategory = categoryRepo.findById(categoryId)
               .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource Not Found"));
       category.setCategoryId(categoryId);
       savedCategory = categoryRepo.save(category);
       return savedCategory;
    }

    @Override
    public String deleteCategory(Long categoryId) {
        Category deletedCategory = categoryRepo.findById(categoryId)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource Not Found"));
        categoryRepo.delete(deletedCategory);

        return "Category with categoryId: " + categoryId + " successfully deleted from DB";

    }

}
