package com.expensemanager.controller.userController;

import com.expensemanager.configs.CustomUserDetails;
import com.expensemanager.dto.ApiResponse;
import com.expensemanager.dto.category.ViewCategoryResponse;
import com.expensemanager.dto.user.UserResponse;
import com.expensemanager.dto.user.userProfileUpdate.UpdateProfileRequest;
import com.expensemanager.entity.category.Category;
import com.expensemanager.entity.user.User;
import com.expensemanager.service.categoryService.CategoryService;
import com.expensemanager.service.userService.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;


@Slf4j
@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    private final CategoryService categoryService;

    public UserController(UserService userService,CategoryService categoryService) {
        this.userService = userService;
        this.categoryService = categoryService;
    }

    @GetMapping("/me")  //get user
    public ResponseEntity<ApiResponse<UserResponse>> authenticatedUser() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails  = (CustomUserDetails) authentication.getPrincipal();

        return ResponseEntity.ok(new ApiResponse<>(true, "User details fetched successfully", new UserResponse(
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getName(),
                userDetails.getIncomeLimit()
        )));
    }

    @PutMapping("/me") //update user
    public ResponseEntity<?> update(@Valid @RequestBody UpdateProfileRequest upd, Authentication authentication) {

        CustomUserDetails userDetails  = (CustomUserDetails) authentication.getPrincipal();
        User u = userService.updateProfile(userDetails.getId(), upd);

        UserResponse r = new UserResponse();
        r.setId(u.getId());
        r.setName(u.getName());
        r.setEmail(u.getEmail());
        r.setIncomeLimit(u.getIncomeLimit());
        return ResponseEntity.ok(new ApiResponse<>(true, "User Successfully updated.", r));
    }

    @GetMapping("/me/categories") // get user categories
    public ResponseEntity<ApiResponse<Set<ViewCategoryResponse>>> viewAUserCategories() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails  = (CustomUserDetails) authentication.getPrincipal();
        User currentUser = userDetails.getUser();

        Set<Category> userCategories = categoryService.getCategoriesByUser(currentUser);
        Set<ViewCategoryResponse> viewCategoryResponseOfUsers = userCategories.stream().map((category -> new ViewCategoryResponse(category.getId(), category.getCategoryName())))
                .collect(Collectors.toSet());

        return ResponseEntity.ok(new ApiResponse<>(true, "Categories fetched for given user", viewCategoryResponseOfUsers));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/test")
    public ResponseEntity<BigDecimal> getIncome(Authentication authentication) {
        CustomUserDetails userDetails  = (CustomUserDetails) authentication.getPrincipal();
        return ResponseEntity.ok(userDetails.getIncomeLimit());
    }
}

