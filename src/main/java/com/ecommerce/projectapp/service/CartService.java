package com.ecommerce.projectapp.service;

import com.ecommerce.projectapp.exception.ProductException;
import com.ecommerce.projectapp.model.Cart;
import com.ecommerce.projectapp.model.CartItem;
import com.ecommerce.projectapp.model.Product;
import com.ecommerce.projectapp.model.User;
import com.ecommerce.projectapp.request.AddItemRequest;

public interface CartService {

    public CartItem addCartItem(User user, Product product, String size, int quantity) throws ProductException;

    public Cart findUserCart(User user);
}