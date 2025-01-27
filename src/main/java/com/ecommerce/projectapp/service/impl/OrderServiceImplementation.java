package com.ecommerce.projectapp.service.impl;

import com.ecommerce.projectapp.model.Cart;
import com.ecommerce.projectapp.model.User;
import com.ecommerce.projectapp.repository.AddressRepository;
import com.ecommerce.projectapp.repository.OrderItemRepository;
import com.ecommerce.projectapp.repository.OrderRepository;
import com.ecommerce.projectapp.repository.UserRepository;
import com.ecommerce.projectapp.service.CartService;
import com.ecommerce.projectapp.service.OrderItemService;

public class OrderServiceImplementation implements OrderService {

    private final OrderRepository orderRepository;
    private final CartService cartService;
    private final AddressRepository addressRepository;
    private final UserRepository userRepository;
    private final OrderItemService orderItemService;
    private final OrderItemRepository orderItemRepository;

    public Set<Order> createOrder(User user, Address shipAddress, Cart cart){

        if(!user.getAddresses().contains(shipAddress)){
            user.getAddresses().add(shipAddress);
        }

        Address address = addressRepository.save(shipAddress);

        Map<Long, List<CartItem>> itemsBySeller = cart.getCartItems().collect(Collectors.groupingBy(item -> item.getProduct().getSeller().getId))
    }
}