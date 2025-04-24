package com.ecommerce.projectapp.service.impl;

import com.ecommerce.projectapp.exception.ProductException;
import com.ecommerce.projectapp.model.Cart;
import com.ecommerce.projectapp.model.CartItem;
import com.ecommerce.projectapp.model.Product;
import com.ecommerce.projectapp.model.User;
import com.ecommerce.projectapp.repository.CartItemRepository;
import com.ecommerce.projectapp.repository.CartRepository;
import com.ecommerce.projectapp.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    @Override
    public CartItem addCartItem(User user, Product product, String size, int quantity) throws ProductException {
        Cart cart = findUserCart(user);

        CartItem existingItem = cartItemRepository.findByCartAndProductAndSize(cart, product, size);

        if (existingItem != null) {
            return existingItem;
        }

        CartItem cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItem.setQuantity(quantity);
        cartItem.setUserId(user.getId());
        cartItem.setSellingPrice(quantity * product.getSellingPrice());
        cartItem.setMrpPrice(quantity * product.getMrpPrice());
        cartItem.setSize(size);
        cartItem.setCart(cart);

        cart.getCartItems().add(cartItem);

        return cartItemRepository.save(cartItem);
    }

    @Override
    public Cart findUserCart(User user) {
        Cart cart = cartRepository.findByUserId(user.getId());

        int totalMrp = 0;
        int totalSelling = 0;
        int totalQuantity = 0;

        for (CartItem item : cart.getCartItems()) {
            totalMrp += item.getMrpPrice();
            totalSelling += item.getSellingPrice();
            totalQuantity += item.getQuantity();
        }

        cart.setTotalMrpPrice(totalMrp);
        cart.setTotalItem(totalQuantity);
        cart.setTotalSellingPrice(totalSelling - cart.getCouponPrice());
        cart.setDiscount(calculateDiscountPercentage(totalMrp, totalSelling));

        return cartRepository.save(cart);
    }

    private int calculateDiscountPercentage(int totalPrice, int totalDiscountPrice) {
        if (totalPrice <= 0) return 0;
        double discount = totalPrice - totalDiscountPrice;
        return (int) ((discount / totalPrice) * 100);
    }
}

