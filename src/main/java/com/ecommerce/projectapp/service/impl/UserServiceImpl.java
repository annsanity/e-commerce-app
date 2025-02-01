package com.ecommerce.projectapp.service.impl;

import com.ecommerce.projectapp.config.JwtProvider;
import com.ecommerce.projectapp.exception.UserException;
import com.ecommerce.projectapp.model.User;
import com.ecommerce.projectapp.repository.PasswordResetTokenRepository;
import com.ecommerce.projectapp.repository.UserRepository;
import com.ecommerce.projectapp.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private JwtProvider jwtProvider;
    private PasswordEncoder passwordEncoder;
    private PasswordResetTokenRepository passwordResetTokenRepository;
    private JavaMailSender javaMailSender;

    @Override
    public User findUserProfileByJwt(String jwt) throws UserException {

        String email = jwtProvider.getEmailFromJwtToken(jwt);
        User user = userRepository.findByEmail(email);

        if(user == null) {
            throw new UserException("User not exist with email " + email);
        }
        return null;
    }

    @Override
    public User findUserByEmail(String email) throws UserException {

        User user = userRepository.findByEmail(email);

        if (user != null) {
            return user;
        }

        throw new UserException("User not exist with username " + email);
    }

}
