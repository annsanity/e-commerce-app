package com.ecommerce.projectapp.service;

import com.ecommerce.projectapp.exception.UserException;
import com.ecommerce.projectapp.model.User;

public interface UserService {
    User findUserProfileByJwt(String jwt) throws UserException;
}
