package com.expensemanager.repository.categoryRepository;

import com.expensemanager.entity.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface CategoryRepository extends JpaRepository<Category, String> {
    Optional<Category> findByUserIdAndId(String userId, String id);
    Optional<Category> findByCategoryNameAndUserId(String categoryName, String id);
    Set<Category> findCategoryByUserId(String userId);
}
