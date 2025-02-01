package com.ecommerce.projectapp.service.impl;

import com.ecommerce.projectapp.exception.ProductException;
import com.ecommerce.projectapp.model.Cart;
import com.ecommerce.projectapp.model.CartItem;
import com.ecommerce.projectapp.model.Product;
import com.ecommerce.projectapp.model.User;
import com.ecommerce.projectapp.service.CartService;

public class CartServiceImpl implements CartService {
    @Override
    public CartItem addCartItem(User user, Product product, String size, int quantity) throws ProductException {
        return null;
    }

    @Override
    public Cart findUserCart(User user) {
        return null;
    }
}
