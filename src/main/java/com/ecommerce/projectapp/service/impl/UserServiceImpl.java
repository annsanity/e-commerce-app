package com.ecommerce.projectapp.service.impl;

import com.ecommerce.projectapp.config.JwtProvider;
import com.ecommerce.projectapp.exception.UserException;
import com.ecommerce.projectapp.model.User;
import com.ecommerce.projectapp.repository.UserRepository;
import com.ecommerce.projectapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;

    @Override
    public User findUserProfileByJwt(String jwt) throws UserException {
        String email = jwtProvider.getEmailFromJwtToken(jwt);
        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new UserException("User does not exist with email: " + email);
        }

        return user;
    }
}
