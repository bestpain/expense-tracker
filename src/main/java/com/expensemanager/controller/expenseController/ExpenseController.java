package com.expensemanager.controller.expenseController;

import com.expensemanager.configs.CustomUserDetails;
import com.expensemanager.dto.ApiResponse;
import com.expensemanager.dto.category.CategoryWithExpensesResponse;
import com.expensemanager.dto.expense.AddExpenseRequest;
import com.expensemanager.dto.expense.SimpleExpenseResponse;
import com.expensemanager.dto.expense.ViewExpenseResponse;
import com.expensemanager.service.categoryService.CategoryService;
import com.expensemanager.service.expenseService.ExpenseService;
import com.expensemanager.service.userService.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/api/expense")
@Validated
public class ExpenseController {

    private final ExpenseService expenseService;
    private final UserService userService;
    private final CategoryService categoryService;

    public ExpenseController(ExpenseService expenseService, UserService userService,CategoryService categoryService) {
        this.expenseService = expenseService;
        this.userService = userService;
        this.categoryService = categoryService;
    }

    @PostMapping("/addexpense/{categoryId}") //add expense
    public ResponseEntity<ApiResponse<SimpleExpenseResponse>> saveExpense(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                                       @RequestBody @Valid AddExpenseRequest expenseBody, @PathVariable String categoryId) {


        SimpleExpenseResponse simpleExpenseResponse = expenseService.saveExpense(expenseBody, userDetails.getUser(), categoryId);

        return ResponseEntity.ok(new ApiResponse<>(true, "Expense added.", simpleExpenseResponse));
    }

    @GetMapping("/viewexpenses") //view expense
    public ResponseEntity<ApiResponse<SimpleExpenseResponse>> getUserExpenses(@AuthenticationPrincipal CustomUserDetails userDetails) {
        Set<String> categoryIdsList = categoryService.getCategoriesIdsByUser(userDetails.getId());
        List<SimpleExpenseResponse> expenses = expenseService.getExpensesByCategoryList(categoryIdsList);
        log.info("expenses {}", expenses);
//        List<ViewExpenseResponse> expenseResponses = userExpenses.stream().map(expense ->
//                new ViewExpenseResponse(expense.getId(), expense.getDescription(), expense.getAmount(), expense.getExpenseDateTime(), userId,
//                        new ViewCategory(expense.getCategory().getId(), expense.getCategory().getCategoryName()))).collect(Collectors.toList());

        return ResponseEntity.ok(new ApiResponse(true, "User expenses fetched", expenses));
    }

    @GetMapping("/userexpenses")
    public ResponseEntity<ApiResponse<List<ViewExpenseResponse>>> getUserExpensesByUserId(
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        List<ViewExpenseResponse> expenses = expenseService.getUserExpenses(userDetails.getId());

        return ResponseEntity.ok(
                new ApiResponse<>(true, "User expenses fetched", expenses)
        );
    }

    @GetMapping("/by-category")
    public ResponseEntity<ApiResponse<List<CategoryWithExpensesResponse>>> getUserExpensesByCategory(
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        List<CategoryWithExpensesResponse> response = categoryService.getExpensesByUser(userDetails.getId());
        return ResponseEntity.ok(new ApiResponse<>(true, "Expenses fetched category-wise", response));
    }

//    @PutMapping("/updateexpense/{expenseId}") //edit expense including its category
//    public ResponseEntity<ApiResponse<AddExpenseResponse>> updateExpense(@RequestBody @Valid EditExpenseRequest updateExpenseBody, @PathVariable String expenseId,
//                                                                         @AuthenticationPrincipal CustomUserDetails userDetails) {
//        AddExpenseResponse updatedExpense = expenseService.updateExpense(expenseId, userDetails.getId(), updateExpenseBody);
//        return ResponseEntity.ok(new ApiResponse<>(true, "Expense updated.", updatedExpense));
//    }

//    @DeleteMapping("/deleteexpense/{expenseId}") //delete expense
//    public ResponseEntity<?> deleteExpense(@PathVariable
//                                           @Pattern(
//                                                   regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[1-5][0-9a-fA-F]{3}-[89abAB][0-9a-fA-F]{3}-[0-9a-fA-F]{12}$",
//                                                   message = "Expense ID must be a valid UUID"
//                                           )
//                                           String expenseId, @AuthenticationPrincipal CustomUserDetails userDetails) {
//
//        boolean deleted = expenseService.deleteExpense(expenseId, userDetails.getId());
//
//        if (deleted) {
//            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
//        } else {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Expense not found"));
//        }
//    }
}
