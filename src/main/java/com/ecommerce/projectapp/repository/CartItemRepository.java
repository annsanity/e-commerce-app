package com.ecommerce.projectapp.repository;

import com.ecommerce.projectapp.model.Cart;
import com.ecommerce.projectapp.model.CartItem;
import com.ecommerce.projectapp.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    CartItem findByCartAndProductAndSize(Cart cart, Product product, String size);
}
