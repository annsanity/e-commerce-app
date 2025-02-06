package com.ecommerce.projectapp.service.impl;

import com.ecommerce.projectapp.config.JwtProvider;
import com.ecommerce.projectapp.domain.USER_ROLE;
import com.ecommerce.projectapp.exception.SellerException;
import com.ecommerce.projectapp.exception.UserException;
import com.ecommerce.projectapp.model.Cart;
import com.ecommerce.projectapp.model.User;
import com.ecommerce.projectapp.model.VerificationCode;
import com.ecommerce.projectapp.repository.CartRepository;
import com.ecommerce.projectapp.repository.UserRepository;
import com.ecommerce.projectapp.repository.VerificationCodeRepository;
import com.ecommerce.projectapp.request.LoginRequest;
import com.ecommerce.projectapp.request.SignupRequest;
import com.ecommerce.projectapp.response.AuthResponse;
import com.ecommerce.projectapp.service.AuthService;
import com.ecommerce.projectapp.service.EmailService;
import com.ecommerce.projectapp.service.UserService;
import com.ecommerce.projectapp.utils.OtpUtils;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserService userService;

    private final VerificationCodeRepository verificationCodeRepository;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    private final JwtProvider jwtProvider;
    private final CustomerUserServiceImpl customUserDetails;
    private final CartRepository cartRepository;

    @Override
    public void sentLoginOtp(String email) throws UserException, MessagingException {

        String SIGNING_PREFIX = "signing_";

        if(email.startsWith(SIGNING_PREFIX)) {
            email = email.substring((SIGNING_PREFIX.length()));
            userService.findUserByEmail(email);
        }

        VerificationCode isExist = verificationCodeRepository.findByEmail(email);

        if(isExist != null) {
            verificationCodeRepository.delete(isExist);
        }

        String otp = OtpUtils.generateOTP();

        VerificationCode verificationCode = new VerificationCode();
        verificationCode.setOtp(otp);
        verificationCode.setEmail(email);
        verificationCodeRepository.save(verificationCode);

        String subject = "Login/Signup OTP for shopping";
        String text = "Your login OTP is -";
        emailService.sendVerificationOtpEmail(email, otp, subject, text);

    }

    @Override
    public String createUser(SignupRequest req) throws SellerException {

        String email = req.getEmail();
        String fullName = req.getFullName();
        String otp = req.getOtp();
        String mobile = req.getMobile();

        VerificationCode verificationCode = verificationCodeRepository.findByEmail(email);

        if(verificationCode == null || !verificationCode.getOtp().equals(otp)) {
            throw new SellerException("Wrong OTP");
        }

        User user = userRepository.findByEmail(email);

        if(user == null) {

            User createdUser = new User();
            createdUser.setEmail(email);
            createdUser.setFullName(fullName);
            createdUser.setRole(USER_ROLE.ROLE_CUSTOMER);
            createdUser.setMobile(mobile);
            createdUser.setPassword(passwordEncoder.encode(otp));

            user = userRepository.save(createdUser);
            Cart cart = new Cart();
            cart.setUser(user);
            cartRepository.save(cart);

        }

        List<GrantedAuthority> authorities = new ArrayList<>();

        authorities.add(new SimpleGrantedAuthority(
                USER_ROLE.ROLE_CUSTOMER.toString()));

        Authentication authentication = new UsernamePasswordAuthenticationToken(email, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return jwtProvider.generateToken(authentication);
    }

    @Override
    public AuthResponse signIn(LoginRequest req) throws SellerException {

        String userName = req.getEmail();
        String otp = req.getOtp();

        System.out.println(userName + " ----- " + otp);

        Authentication authentication = authenticate(userName, otp);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtProvider.generateToken(authentication);
        AuthResponse authResponse = new AuthResponse();

        authResponse.setMessage("Login Success");
        authResponse.setJwt(token);
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();


        String roleName = authorities.isEmpty() ? null : authorities.iterator().next().getAuthority();


        authResponse.setRole(USER_ROLE.valueOf(roleName));

        return authResponse;
    }

    private Authentication authenticate(String userName, String otp) throws SellerException  {

        UserDetails userDetails = customUserDetails.loadUserByUsername(userName);
        if(userDetails == null) {
            throw new BadCredentialsException("Invalid username or password");
        }

        VerificationCode verificationCode = verificationCodeRepository.findByEmail(userName);

        if(verificationCode == null || !verificationCode.getOtp().equals(otp)) {
            throw  new SellerException("Wrong OTP");
        }

        return  new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
