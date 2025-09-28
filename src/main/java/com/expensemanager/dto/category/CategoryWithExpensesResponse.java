package com.expensemanager.dto.category;

import com.expensemanager.dto.expense.ExpenseResponse;

import java.util.List;

public record CategoryWithExpensesResponse(
        String categoryId,
        String categoryName,
        List<ExpenseResponse> expenses
) {}
