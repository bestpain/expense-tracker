package com.expensemanager.repository.expenseRepository;

import com.expensemanager.dto.expense.SimpleExpenseResponse;
import com.expensemanager.dto.expense.ViewExpenseResponse;
import com.expensemanager.entity.expense.Expense;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, String> {
//    @Query("SELECT e FROM Expense e JOIN FETCH e.category WHERE e.user.id = :userId")
//    List<Expense> findAllByUserId(String userId);

    @Query("""
            SELECT new com.expensemanager.dto.expense.ViewExpenseResponse(
                e.id,
                e.description,
                e.amount,
                e.expenseDateTime,
                new com.expensemanager.dto.category.ViewCategoryResponse(
                    c.id,
                    c.categoryName)
            )
            FROM Expense e
            JOIN e.category c
            WHERE c.user.id = :userId
            """)
    List<ViewExpenseResponse> findExpensesByUserId(@Param("userId") String userId);

//    @Query("SELECT e FROM Expense e WHERE e.id = :expenseId AND e.user.id = :userId")
//    Optional<Expense> findByIdAndUserId(String expenseId, String userId);

//    @Modifying
//    @Transactional
//    @Query("DELETE FROM Expense e WHERE e.id = :expenseId AND e.user.id = :userId")
//    int deleteByIdAndUserId(String expenseId, String userId);


    List<Expense> findByCategoryIdIn(Set<String> categoryIds);

}
