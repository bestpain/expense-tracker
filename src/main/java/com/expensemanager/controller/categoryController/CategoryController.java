package com.expensemanager.controller.categoryController;

import com.expensemanager.dto.ApiResponse;
import com.expensemanager.dto.category.AddCategoryRequest;
import com.expensemanager.dto.category.AddCategoryResponse;
import com.expensemanager.dto.category.ViewAllCategory;
import com.expensemanager.dto.category.ViewCategory;
import com.expensemanager.dto.user.SimpleUserResponse;
import com.expensemanager.entity.category.Category;
import com.expensemanager.entity.user.User;
import com.expensemanager.service.categoryService.CategoryService;
import com.expensemanager.service.userService.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        User currentUser = (User) authentication.getPrincipal();

        Category savedCategory = categoryService.saveCategory(currentUser, categoryRequest.getCategoryName());

        AddCategoryResponse response = new AddCategoryResponse(
                savedCategory.getId(),
                savedCategory.getCategoryName(),
                new SimpleUserResponse(currentUser.getId(), currentUser.getName())
        );

        return ResponseEntity.ok(new ApiResponse<>(true, "Category Added", response));
    }

//    @GetMapping("/user/all")
//    public ResponseEntity<ApiResponse> getAllCategories(@RequestParam String userId){
//
//    }
}
