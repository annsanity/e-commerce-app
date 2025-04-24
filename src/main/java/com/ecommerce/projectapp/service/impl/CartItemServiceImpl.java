package com.ecommerce.projectapp.service.impl;

import com.ecommerce.projectapp.exception.CartItemException;
import com.ecommerce.projectapp.exception.UserException;
import com.ecommerce.projectapp.model.CartItem;
import com.ecommerce.projectapp.model.User;
import com.ecommerce.projectapp.repository.CartItemRepository;
import com.ecommerce.projectapp.service.CartItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartItemServiceImpl implements CartItemService {

    private final CartItemRepository cartItemRepository;

    @Override
    public CartItem updateCartItem(Long userId, Long id, CartItem cartItem) throws CartItemException, UserException {
        CartItem item = cartItemRepository.findById(id)
                .orElseThrow(() -> new CartItemException("Item not found. ID: " + id));

        User cartItemUser = item.getCart().getUser();
        if (!cartItemUser.getId().equals(userId)) {
            throw new CartItemException("Unauthorized to update this item.");
        }

        item.setQuantity(cartItem.getQuantity());
        item.setMrpPrice(item.getQuantity() * item.getProduct().getMrpPrice());
        item.setSellingPrice(item.getQuantity() * item.getProduct().getSellingPrice());

        return cartItemRepository.save(item);
    }

    @Override
    public void removeCartItem(Long userId, Long cartItemId) throws CartItemException, UserException {
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new CartItemException("Item not found. ID: " + cartItemId));

        User cartItemUser = cartItem.getCart().getUser();
        if (!cartItemUser.getId().equals(userId)) {
            throw new UserException("Unauthorized to remove this item.");
        }

        cartItemRepository.deleteById(cartItem.getId());
    }
}