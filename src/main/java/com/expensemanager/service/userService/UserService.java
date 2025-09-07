package com.expensemanager.service.userService;

import com.expensemanager.dto.user.userRegister.UserRegisterRequest;
import com.expensemanager.entity.user.User;
import com.expensemanager.repository.userRepository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public User registerUser(UserRegisterRequest userBody) {
        User user = User.builder().name(userBody.getName()).email(userBody.getEmail())
                .password(userBody.getPassword()).build();
        return userRepository.save(user);// save to DB
    }
}
