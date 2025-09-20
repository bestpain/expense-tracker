package com.expensemanager.controller.adminController;

import com.expensemanager.dto.ApiResponse;
import com.expensemanager.dto.category.ViewAllCategory;
import com.expensemanager.dto.user.SimpleUserResponse;
import com.expensemanager.entity.category.Category;
import com.expensemanager.entity.user.User;
import com.expensemanager.service.categoryService.CategoryService;
import com.expensemanager.service.userService.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private CategoryService categoryService;

    public AdminController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/dashboard")
    public ResponseEntity<String> getAdminDashboard() {
        return ResponseEntity.ok("Welcome, Admin!");
    }

    @GetMapping("/allcategory")
    public ResponseEntity<ApiResponse> getAllCategories() {
        List<Category> categoryList = categoryService.getAllCategories();
        List<ViewAllCategory> allCategories = categoryList.stream().map(category ->
                new ViewAllCategory(category.getId(), category.getCategoryName(), new SimpleUserResponse(category.getUser().getId(),category.getUser().getName()))).toList();
        return ResponseEntity.ok(new ApiResponse(true, "All Categories", allCategories));
    }
}
