package com.ecommerce.projectapp.service;

import com.ecommerce.projectapp.exception.CartItemException;
import com.ecommerce.projectapp.exception.UserException;
import com.ecommerce.projectapp.model.Cart;
import com.ecommerce.projectapp.model.CartItem;
import com.ecommerce.projectapp.model.Product;

public interface CartItemService {

    public CartItem updateCartItem(Long userId, Long id, CartItem cartItem) throws CartItemException, UserException;

    public void removeCartItem(Long userId,Long cartItemId) throws CartItemException, UserException;

    public CartItem findCartItemById(Long cartItemId) throws CartItemException;

}