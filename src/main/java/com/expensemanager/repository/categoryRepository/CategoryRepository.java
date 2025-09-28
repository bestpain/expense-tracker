package com.expensemanager.repository.categoryRepository;

import com.expensemanager.entity.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface CategoryRepository extends JpaRepository<Category, String> {

    @Query("SELECT c FROM Category c WHERE c.id = :categoryId AND c.user.id = :userId")
    Optional<Category> findByIdAndUserId(String categoryId, String userId);

//    @Query("SELECT c FROM Category c WHERE c.categoryName = :categoryName AND c.user.id = :userId")
//    Optional<Category> findByCategoryNameAndUserId(String categoryName, String userId);

    boolean existsByCategoryNameAndUserId(String categoryName, String userId);

    @Query("SELECT c from Category c WHERE c.user.id = :userId")
    Set<Category> findCategoryByUserId(String userId);

    @Query("SELECT c.id FROM Category c WHERE c.user.id = :userId")
    Set<String> findCategoryIdsByUserId(String userId);

    @Query("SELECT DISTINCT c FROM Category c LEFT JOIN FETCH c.expenses WHERE c.user.id = :userId")
    List<Category> findByUserIdFetchExpenses(@Param("userId") String userId);

}
