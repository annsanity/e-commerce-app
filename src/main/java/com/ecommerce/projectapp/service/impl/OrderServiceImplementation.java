package com.ecommerce.projectapp.service.impl;

import com.ecommerce.projectapp.domain.OrderStatus;
import com.ecommerce.projectapp.exception.OrderException;
import com.ecommerce.projectapp.model.*;
import com.ecommerce.projectapp.repository.AddressRepository;
import com.ecommerce.projectapp.repository.OrderItemRepository;
import com.ecommerce.projectapp.repository.OrderRepository;
import com.ecommerce.projectapp.repository.UserRepository;
import com.ecommerce.projectapp.service.CartService;
import com.ecommerce.projectapp.service.OrderItemService;
import com.ecommerce.projectapp.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
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

//
        return null;
    }

    @Override
    public Order findOrderById(Long orderId) throws OrderException {
        return null;
    }

    @Override
    public List<Order> usersOrderHistory(Long userId) {
        return null;
    }

    @Override
    public List<Order> getShopsOrders(Long sellerId) {
        return null;
    }

    @Override
    public Order updateOrderStatus(Long orderId, OrderStatus orderStatus) throws OrderException {
        return null;
    }

    @Override
    public void deleteOrder(Long orderId) throws OrderException {

    }

    @Override
    public Order cancelOrder(Long orderId, User user) throws OrderException {
        return null;
    }
}