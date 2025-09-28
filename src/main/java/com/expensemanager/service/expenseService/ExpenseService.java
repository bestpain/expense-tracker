package com.expensemanager.service.expenseService;

import com.expensemanager.dto.expense.AddExpenseRequest;
import com.expensemanager.dto.expense.EditExpenseRequest;
import com.expensemanager.dto.expense.SimpleExpenseResponse;
import com.expensemanager.dto.expense.ViewExpenseResponse;
import com.expensemanager.entity.category.Category;
import com.expensemanager.entity.expense.Expense;
import com.expensemanager.entity.user.User;
import com.expensemanager.exception.category.CategoryNotFound;
import com.expensemanager.exception.user.DuplicateResourceException;
import com.expensemanager.exception.user.NotFoundException;
import com.expensemanager.repository.categoryRepository.CategoryRepository;
import com.expensemanager.repository.expenseRepository.ExpenseRepository;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ExpenseService {
    private final ExpenseRepository expenseRepository;
    private final CategoryRepository categoryRepository;

    public ExpenseService(ExpenseRepository expenseRepository, CategoryRepository categoryRepository) {
        this.expenseRepository = expenseRepository;
        this.categoryRepository = categoryRepository;
    }

    public SimpleExpenseResponse saveExpense(AddExpenseRequest expenseBody, User currentUser, String categoryId) {

        Category category = categoryRepository.findByIdAndUserId(categoryId, currentUser.getId())
                .orElseThrow(() -> new CategoryNotFound("Category is invalid."));

        Expense expense = Expense.of(expenseBody.amount(), expenseBody.description(),
                category, expenseBody.expenseDateTime());

        Expense savedExpense = expenseRepository.save(expense);
        return new SimpleExpenseResponse(savedExpense.getId(), savedExpense.getDescription(), savedExpense.getAmount()
                , savedExpense.getExpenseDateTime(), categoryId);
    }

    public List<SimpleExpenseResponse> getExpensesByCategoryList(Set<String> categoryIds){
        List<Expense> expensesForCategoriesIds = expenseRepository.findByCategoryIdIn(categoryIds);

        return expensesForCategoriesIds.stream().map(expense -> new SimpleExpenseResponse(expense.getCategoryId(),
                expense.getDescription(),expense.getAmount(),expense.getExpenseDateTime(),expense.getCategoryId())).collect(Collectors.toList());
    }

    public List<ViewExpenseResponse> getUserExpenses(String userId) {
        return expenseRepository.findExpensesByUserId(userId);
    }

//    public List<ViewExpenseResponse> viewUserExpenses(String userId) {
//        return expenseRepository.findAllExpenseResponsesByUserId(userId);
//    }

//    public AddExpenseResponse updateExpense(String expenseId, String userId, EditExpenseRequest updatedExpenseBody) {
//
//        Expense expense = expenseRepository.findByIdAndUserId(expenseId, userId).orElseThrow(() -> new NotFoundException("Expense not found"));
//        if (expense.getCategoryId().equals(updatedExpenseBody.categoryId()))
//            throw new DuplicateResourceException("Category Can not be same.");
//
//        if (updatedExpenseBody.amount() != null) expense.setAmount(updatedExpenseBody.amount());
//        if (updatedExpenseBody.description() != null) expense.setDescription(updatedExpenseBody.description());
//        if (updatedExpenseBody.expenseDateTime() != null)
//            expense.setExpenseDateTime(updatedExpenseBody.expenseDateTime());
//        if (updatedExpenseBody.categoryId() != null) {
//            Category category = categoryRepository.findById(updatedExpenseBody.categoryId()).orElseThrow(() -> new CategoryNotFound("Category Not found."));
//            expense.setCategory(category);
//        }
//
//        Expense updatedExpense = expenseRepository.save(expense);
//
//        return new AddExpenseResponse(updatedExpense.getId(), updatedExpense.getDescription(), updatedExpense.getAmount(), updatedExpense.getExpenseDateTime(),
//                userId, updatedExpense.getCategoryId());
//    }

//    public boolean deleteExpense(String expenseId, String userId) {
//        int deletedRows = expenseRepository.deleteByIdAndUserId(expenseId, userId);
//        return deletedRows > 0;
//    }
}
