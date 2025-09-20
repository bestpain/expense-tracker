package com.expensemanager.repository.expenseRepository;

import com.expensemanager.entity.expense.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, String> {
}
