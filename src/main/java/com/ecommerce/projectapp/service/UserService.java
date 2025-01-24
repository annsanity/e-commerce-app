package com.ecommerce.projectapp.service;

import com.ecommerce.projectapp.exception.UserException;
import com.ecommerce.projectapp.model.User;

public interface UserService {

    public User findUserProfileByJwt(String jwt) throws UserException;

    public User findUserByEmail(String email) throws UserException;

}
