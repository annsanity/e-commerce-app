package com.ecommerce.projectapp.service;

import com.ecommerce.projectapp.exception.SellerException;
import com.ecommerce.projectapp.exception.UserException;
import com.ecommerce.projectapp.request.LoginRequest;
import com.ecommerce.projectapp.request.SignupRequest;
import jakarta.mail.MessagingException;

public interface AuthService {

    void sentLoginOtp(String email) throws UserException, MessagingException;
    String createUser(SignupRequest req) throws SellerException;
    AuthResponse signup(LoginRequest req) throws SellerException;
}
