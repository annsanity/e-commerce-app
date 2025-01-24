package com.ecommerce.projectapp.service.impl;

import com.ecommerce.projectapp.exception.CartItemException;
import com.ecommerce.projectapp.exception.UserException;
import com.ecommerce.projectapp.model.CartItem;
import com.ecommerce.projectapp.repository.CartItemRepository;
import com.ecommerce.projectapp.service.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartItemServiceImplementation implements CartItemService {

    private CartItemRepository cartItemRepository;

    @Autowired
    public CartItemServiceImplementation(CartItemRepository cartItemRepository) {
        this.cartItemRepository = cartItemRepository;
    }

    @Override
    public CartItem updateCartItem(Long userId, Long id, CartItem cartItem) throws CartItemException, UserException {
        return null;
    }

    @Override
    public void removeCartItem(Long userId, Long cartItemId) throws CartItemException, UserException {

    }

    @Override
    public CartItem findCartItemById(Long cartItemId) throws CartItemException {
        return null;
    }
}
