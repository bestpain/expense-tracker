package com.expensemanager.service.categoryService;

import com.expensemanager.dto.category.CategoryWithExpensesResponse;
import com.expensemanager.dto.expense.ExpenseResponse;
import com.expensemanager.entity.category.Category;
import com.expensemanager.entity.user.User;
import com.expensemanager.exception.category.DuplicateCategoryName;
import com.expensemanager.exception.user.DuplicateResourceException;
import com.expensemanager.repository.categoryRepository.CategoryRepository;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class CategoryService {
    private CategoryRepository categoryRepository;
    private EntityManager entityManager;

    public CategoryService(CategoryRepository categoryRepository, EntityManager entityManager) {
        this.categoryRepository = categoryRepository;
        this.entityManager = entityManager;
    }

    public Category saveCategory(User user, String categoryName) {
        boolean isCategoryPresent = categoryRepository.existsByCategoryNameAndUserId(categoryName, user.getId());
        if (isCategoryPresent) throw new DuplicateResourceException("Category Already exists.");

//        The user object you pass comes from Spring Security(CustomUserDetails), not JPA.
//        Hibernate sees Category.user pointing to a detached entity.
//        Hibernate doesn’t trust detached entities —it runs:   select * from users where id = ?
//        entityManager.getReference(User.class, id) does not run a SELECT.
//        Instead, Hibernate creates a lazy proxy object(basically: “fake user” with only the id filled).
//        When saving Category, Hibernate now sees:
//        "userRef is already a managed reference, I can just use its ID in the INSERT."
//        No need to validate it with a SELECT.
        User userRef = entityManager.getReference(User.class, user.getId());
        Category category = Category.of(categoryName, userRef);

        return categoryRepository.save(category);
    }

    public Set<Category> getCategoriesByUser(User user) {
        return categoryRepository.findCategoryByUserId(user.getId());
    }

    public Set<String> getCategoriesIdsByUser(String userId) {
        return categoryRepository.findCategoryIdsByUserId(userId);
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }
    public List<CategoryWithExpensesResponse> getExpensesByUser(String userId) {
        List<Category> categories = categoryRepository.findByUserIdFetchExpenses(userId);

        return categories.stream()
                .map(c -> new CategoryWithExpensesResponse(
                        c.getId(),
                        c.getCategoryName(),
                        c.getExpenses().stream()
                                .map(e -> new ExpenseResponse(
                                        e.getId(),
                                        e.getDescription(),
                                        e.getAmount(),
                                        e.getExpenseDateTime()))
                                .toList()
                ))
                .toList();
    }
}
