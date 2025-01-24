package com.ecommerce.projectapp.service;

import com.ecommerce.projectapp.domain.OrderStatus;
import com.ecommerce.projectapp.exception.OrderException;
import com.ecommerce.projectapp.model.Address;
import com.ecommerce.projectapp.model.Cart;
import com.ecommerce.projectapp.model.Order;
import com.ecommerce.projectapp.model.User;

import java.util.List;
import java.util.Set;

public interface OrderService {

    public Set<Order> createOrder(User user, Address shippingAddress, Cart cart);

    public Order findOrderById(Long orderId) throws OrderException;

    public List<Order> usersOrderHistory(Long userId);

    public List<Order>getShopsOrders(Long sellerId);

    public Order updateOrderStatus(Long orderId, OrderStatus orderStatus) throws OrderException;

    public void deleteOrder(Long orderId) throws OrderException;

    Order cancelOrder(Long orderId,User user) throws OrderException;

}
