package com.ecommerce.projectapp.service;

import com.ecommerce.projectapp.exception.WishlistNotFoundException;
import com.ecommerce.projectapp.model.Product;
import com.ecommerce.projectapp.model.User;
import com.ecommerce.projectapp.model.Wishlist;

public interface WishlistService {

    Wishlist getWishlistByUserId(User user);

    Wishlist addProductToWishlist(User user, Product product) throws WishlistNotFoundException;
}
