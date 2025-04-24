package com.ecommerce.projectapp.service.impl;

import com.ecommerce.projectapp.exception.WishlistNotFoundException;
import com.ecommerce.projectapp.model.Product;
import com.ecommerce.projectapp.model.User;
import com.ecommerce.projectapp.model.Wishlist;
import com.ecommerce.projectapp.repository.WishlistRepository;
import com.ecommerce.projectapp.service.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WishlistServiceImpl implements WishlistService {

    private final WishlistRepository wishlistRepository;

    @Override
    public Wishlist getWishlistByUserId(User user) {
        Wishlist wishlist = wishlistRepository.findByUserId(user.getId());
        if (wishlist == null) {
            wishlist = new Wishlist();
            wishlist.setUser(user);
            wishlist = wishlistRepository.save(wishlist);
        }
        return wishlist;
    }

    @Override
    public Wishlist addProductToWishlist(User user, Product product) throws WishlistNotFoundException {
        Wishlist wishlist = getWishlistByUserId(user);
        if (wishlist.getProducts().contains(product)) {
            wishlist.getProducts().remove(product);
        } else {
            wishlist.getProducts().add(product);
        }
        return wishlistRepository.save(wishlist);
    }
}

