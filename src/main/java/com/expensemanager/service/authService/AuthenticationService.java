package com.expensemanager.service.authService;


import com.expensemanager.dto.user.userLogin.UserLoginRequest;
import com.expensemanager.dto.user.userRegister.UserRegisterRequest;
import com.expensemanager.dto.user.userRegister.UserRegisterResponse;
import com.expensemanager.entity.user.User;
import com.expensemanager.exception.user.DuplicateResourceException;
import com.expensemanager.repository.userRepository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    public AuthenticationService(
            UserRepository userRepository,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public UserRegisterResponse signup(UserRegisterRequest userBody) {
        if (userRepository.findByEmail(userBody.getEmail()).isPresent()) {
            throw new DuplicateResourceException("Email already registered");
        }

        User user = User.of(userBody.getName(), userBody.getEmail(),
                passwordEncoder.encode(userBody.getPassword()), userBody.getIncomeLimit());

        User savedUser = userRepository.save(user);

        return new UserRegisterResponse(savedUser.getId(), savedUser.getName(), savedUser.getEmail(), savedUser.getCreatedAt());
    }

    public Authentication authenticate(UserLoginRequest input) {
        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getEmail(),
                        input.getPassword()
                )
        );
    }
}
