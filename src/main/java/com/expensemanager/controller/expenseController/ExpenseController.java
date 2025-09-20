package com.expensemanager.controller.expenseController;

import com.expensemanager.dto.ApiResponse;
import com.expensemanager.dto.expense.AddExpenseRequest;
import com.expensemanager.dto.expense.AddExpenseResponse;
import com.expensemanager.service.expenseService.ExpenseService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/expense")
public class ExpenseController {

    private ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService){
        this.expenseService =  expenseService;
    }

    @PostMapping("/addexpense")
    public ResponseEntity<?> addExpense(@RequestBody @Valid AddExpenseRequest expenseBody){
        AddExpenseResponse addExpenseResponse = expenseService.saveExpense(expenseBody);
        return ResponseEntity.ok(new ApiResponse<>(true, "Expense added.", addExpenseResponse));
    }
}
