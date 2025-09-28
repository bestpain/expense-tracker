package com.expensemanager.service.userService;

import com.expensemanager.dto.user.userLogin.UserLoginRequest;
import com.expensemanager.dto.user.userProfileUpdate.UpdateProfileRequest;
import com.expensemanager.dto.user.userRegister.UserRegisterRequest;
import com.expensemanager.dto.user.userRegister.UserRegisterResponse;
import com.expensemanager.entity.category.Category;
import com.expensemanager.entity.user.User;
import com.expensemanager.exception.user.DuplicateResourceException;
import com.expensemanager.exception.user.NotFoundException;
import com.expensemanager.repository.categoryRepository.CategoryRepository;
import com.expensemanager.repository.userRepository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final CategoryRepository categoryRepository;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager,CategoryRepository categoryRepository) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.categoryRepository=  categoryRepository;
    }

    public Optional<User> findById(String userId) {
        return userRepository.findById(userId);
    }


    public User updateProfile(String userId, UpdateProfileRequest upd) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));
        //update email
        if (upd.getEmail() != null && !upd.getEmail().equalsIgnoreCase(user.getEmail())) {
            if (userRepository.existsByEmailIgnoreCase(upd.getEmail()))
                throw new DuplicateResourceException(upd.getEmail()+ " is invalid");
            user.setEmail(upd.getEmail().toLowerCase());
        }
        if (upd.getName() != null) user.setName(upd.getName());
        if (upd.getIncomeLimit() != null) user.setIncomeLimit(upd.getIncomeLimit());
        if (upd.getPassword() != null) user.setPassword(passwordEncoder.encode(upd.getPassword()));
        return userRepository.save(user);
    }
}
