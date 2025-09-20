package com.expensemanager.service.expenseService;

import com.expensemanager.dto.expense.AddExpenseRequest;
import com.expensemanager.dto.expense.AddExpenseResponse;
import com.expensemanager.entity.category.Category;
import com.expensemanager.entity.expense.Expense;
import com.expensemanager.entity.user.User;
import com.expensemanager.exception.category.CategoryNotFound;
import com.expensemanager.repository.categoryRepository.CategoryRepository;
import com.expensemanager.repository.expenseRepository.ExpenseRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class ExpenseService {
    private ExpenseRepository expenseRepository;
    private CategoryRepository categoryRepository;

    public ExpenseService(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    public AddExpenseResponse saveExpense(AddExpenseRequest expenseBody) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();

        // check if categoryId belongs to user
        //select * from category where id = and user id
        Category category = categoryRepository.findByUserIdAndId(currentUser.getId(),expenseBody.getCategoryId())
                .orElseThrow(()-> new CategoryNotFound("Category is invalid."));

        Expense expense = Expense.of(expenseBody.getAmount(), expenseBody.getDescription(), currentUser,
                category);

        Expense savedExpense = expenseRepository.save(expense);
        return new AddExpenseResponse(savedExpense.getId(), savedExpense.getDescription(), savedExpense.getAmount()
                , savedExpense.getExpenseDateTime(), savedExpense.getUser(), savedExpense.getCategory());
    }
}
