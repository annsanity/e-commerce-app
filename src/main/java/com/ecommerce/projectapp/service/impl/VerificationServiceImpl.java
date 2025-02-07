package com.ecommerce.projectapp.service.impl;

import com.ecommerce.projectapp.model.VerificationCode;
import com.ecommerce.projectapp.repository.VerificationCodeRepository;
import com.ecommerce.projectapp.service.VerificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VerificationServiceImpl implements VerificationService {

    private final VerificationCodeRepository verificationCodeRepository;
    @Override
    public VerificationCode createVerificationCode(String otp, String email) {
        VerificationCode isExist = verificationCodeRepository.findByEmail(email);

        if(isExist != null) {
            verificationCodeRepository.delete(isExist);
        }

        VerificationCode verificationCode = new VerificationCode();
        verificationCode.setOtp(otp);
        verificationCode.setEmail(email);

        return verificationCodeRepository.save(verificationCode);

    }
}
