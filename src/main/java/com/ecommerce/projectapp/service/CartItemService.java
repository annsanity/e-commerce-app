package com.ecommerce.projectapp.service;

import com.ecommerce.projectapp.exception.CartItemException;
import com.ecommerce.projectapp.exception.UserException;
import com.ecommerce.projectapp.model.CartItem;

public interface CartItemService {

    CartItem updateCartItem(Long userId, Long id, CartItem cartItem) throws CartItemException, UserException;

    void removeCartItem(Long userId, Long cartItemId) throws CartItemException, UserException;
}
