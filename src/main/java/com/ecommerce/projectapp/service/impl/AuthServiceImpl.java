package com.ecommerce.projectapp.service.impl;

import com.ecommerce.projectapp.config.JwtProvider;
import com.ecommerce.projectapp.domain.User;
import com.ecommerce.projectapp.repository.UserRepository;
import com.ecommerce.projectapp.request.LoginRequest;
import com.ecommerce.projectapp.request.SignupRequest;
import com.ecommerce.projectapp.response.AuthResponse;
import com.ecommerce.projectapp.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    @Override
    public String createUser(SignupRequest req) throws Exception {
        if (userRepository.findByEmail(req.getEmail()).isPresent()) {
            throw new Exception("Email already in use");
        }

        User user = new User();
        user.setEmail(req.getEmail());
        user.setPassword(passwordEncoder.encode(req.getPassword()));
        user.setFirstName(req.getFirstName());
        user.setLastName(req.getLastName());

        userRepository.save(user);

        return "User registered successfully";
    }

    @Override
    public AuthResponse signIn(LoginRequest req) throws Exception {
        Optional<User> userOpt = userRepository.findByEmail(req.getEmail());

        if (userOpt.isEmpty() || !passwordEncoder.matches(req.getPassword(), userOpt.get().getPassword())) {
            throw new Exception("Invalid email or password");
        }

        String token = jwtProvider.generateToken(userOpt.get());

        return new AuthResponse(token);
    }
}
