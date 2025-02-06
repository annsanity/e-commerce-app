package com.ecommerce.projectapp.service.impl;

import com.ecommerce.projectapp.exception.CartItemException;
import com.ecommerce.projectapp.exception.UserException;
import com.ecommerce.projectapp.model.CartItem;
import com.ecommerce.projectapp.model.User;
import com.ecommerce.projectapp.repository.CartItemRepository;
import com.ecommerce.projectapp.service.CartItemService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CartItemServiceImpl implements CartItemService {

    private CartItemRepository cartItemRepository;

    @Override
    public CartItem updateCartItem(Long userId, Long id, CartItem cartItem) throws CartItemException, UserException {

        CartItem item = findCartItemById(id);
        User cartItemUser = item.getCart().getUser();

        if (cartItemUser.getId().equals(userId)) {

            item.setQuantity(cartItem.getQuantity());
            item.setMrpPrice(item.getQuantity() * item.getProduct().getMrpPrice());
            item.setSellingPrice(item.getQuantity() * item.getProduct().getSellingPrice());

            return cartItemRepository.save(item);
        }
        else {
            throw new CartItemException("Item cannot be updated!");
        }
    }

    @Override
    public void removeCartItem(Long userId, Long cartItemId) throws CartItemException, UserException {

        CartItem cartItem = findCartItemById(cartItemId);
        User cartItemUser=cartItem.getCart().getUser();

        if (cartItemUser.getId().equals(userId)) {
            cartItemRepository.deleteById(cartItem.getId());
        }
        else {
            throw new UserException("Item cannot be removed!");
        }
    }

    @Override
    public CartItem findCartItemById(Long cartItemId) throws CartItemException {

        Optional<CartItem> optional = cartItemRepository.findById(cartItemId);

        if (optional.isPresent()) {
            return optional.get();
        }

        throw new CartItemException("Item not found. : " + cartItemId);
    }
}
