package com.ecommerce.projectapp.controller;

import com.ecommerce.projectapp.service.CartItemService;
import com.ecommerce.projectapp.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cart_items")
@AllArgsConstructor
public class CartItemController {

    private CartItemService cartItemService;
    private UserService userService;

}
