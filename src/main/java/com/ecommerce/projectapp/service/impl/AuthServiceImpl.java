package com.ecommerce.projectapp.service.impl;

import com.ecommerce.projectapp.exception.SellerException;
import com.ecommerce.projectapp.exception.UserException;
import com.ecommerce.projectapp.request.LoginRequest;
import com.ecommerce.projectapp.request.SignupRequest;
import com.ecommerce.projectapp.response.AuthResponse;
import com.ecommerce.projectapp.service.AuthService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    @Override
    public void sentLoginOtp(String email) throws UserException, MessagingException {

        String
    }

    @Override
    public String createUser(SignupRequest req) throws SellerException {
        return null;
    }

    @Override
    public AuthResponse signIn(LoginRequest req) throws SellerException {
        return null;
    }
}
