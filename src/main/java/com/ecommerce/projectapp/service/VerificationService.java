package com.ecommerce.projectapp.service;

import com.ecommerce.projectapp.model.VerificationCode;

public interface VerificationService {

    VerificationCode createVerificationCode(String otp, String email);
}
