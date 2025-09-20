package com.expensemanager.service.categoryService;

import com.expensemanager.entity.category.Category;
import com.expensemanager.entity.user.User;
import com.expensemanager.exception.category.DuplicateCategoryName;
import com.expensemanager.exception.user.DuplicateResourceException;
import com.expensemanager.repository.categoryRepository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class CategoryService {
    private CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category saveCategory(User user, String categoryName) {
        Optional<Category> existingCategory = categoryRepository.findByCategoryNameAndUserId(categoryName, user.getId());

        if (existingCategory.isPresent()) throw new DuplicateResourceException("Category Already exists.");

        Category category = Category.of(categoryName, user);
        return categoryRepository.save(category);
    }

    public Set<Category> getCategoriesByUser(User user){
        return categoryRepository.findCategoryByUserId(user.getId());
    }

    public List<Category> getAllCategories(){
        return categoryRepository.findAll();
    }
}
