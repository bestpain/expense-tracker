package com.expensemanager.controller.categoryController;

import com.expensemanager.configs.CustomUserDetails;
import com.expensemanager.dto.ApiResponse;
import com.expensemanager.dto.category.AddCategoryRequest;
import com.expensemanager.dto.category.AddCategoryResponse;
import com.expensemanager.dto.user.SimpleUserResponse;
import com.expensemanager.entity.category.Category;
import com.expensemanager.entity.user.User;
import com.expensemanager.service.categoryService.CategoryService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/category")
public class CategoryController {
    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/addcategory")
    public ResponseEntity<ApiResponse<AddCategoryResponse>> createCategoryForUser(@RequestBody @Valid AddCategoryRequest categoryRequest) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails  = (CustomUserDetails) authentication.getPrincipal();

        User currentUser = userDetails.getUser();
        Category savedCategory = categoryService.saveCategory(currentUser, categoryRequest.getCategoryName());

        AddCategoryResponse response = new AddCategoryResponse(
                savedCategory.getId(),
                savedCategory.getCategoryName(),
                new SimpleUserResponse(currentUser.getId(), currentUser.getName())
        );

        return ResponseEntity.ok(new ApiResponse<>(true, "Category Added", response));
    }

    //crud on category
}
