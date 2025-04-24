package com.ecommerce.projectapp.service.impl;

import com.ecommerce.projectapp.domain.OrderStatus;
import com.ecommerce.projectapp.domain.PaymentStatus;
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

import java.util.*;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CartService cartService;
    private final AddressRepository addressRepository;
    private final UserRepository userRepository;
    private final OrderItemRepository orderItemRepository;

    @Override
    public Order createOrder(User user, Address shippingAddress, Cart cart) {
        // Save shipping address if new
        if (!user.getAddresses().contains(shippingAddress)) {
            user.getAddresses().add(shippingAddress);
        }
        Address address = addressRepository.save(shippingAddress);

        // Create a single order for all cart items
        Order order = new Order();
        order.setUser(user);
        order.setShippingAddress(address);
        order.setOrderStatus(OrderStatus.PENDING);
        order.getPaymentDetails().setStatus(PaymentStatus.PENDING);

        // Save order first to get an ID
        Order savedOrder = orderRepository.save(order);

        // Create order items from cart items
        for (CartItem cartItem : cart.getCartItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(savedOrder);
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setSize(cartItem.getSize());
            orderItem.setMrpPrice(cartItem.getMrpPrice());
            orderItem.setSellingPrice(cartItem.getSellingPrice());
            orderItem.setUserId(user.getId());

            // Add to order's items
            savedOrder.getOrderItems().add(orderItem);

            // Save order item
            orderItemRepository.save(orderItem);
        }

        // Calculate order totals
        savedOrder.calculateTotals();

        // Save updated order
        return orderRepository.save(savedOrder);
    }

    @Override
    public Order findOrderById(Long orderId) throws OrderException {
        Optional<Order> optional = orderRepository.findById(orderId);
        if (optional.isPresent()) {
            return optional.get();
        }
        throw new OrderException("Order not found with ID: " + orderId);
    }

    @Override
    public List<Order> usersOrderHistory(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    @Override
    public Order updateOrderStatus(Long orderId, OrderStatus orderStatus) throws OrderException {
        Order order = findOrderById(orderId);
        order.setOrderStatus(orderStatus);
        return orderRepository.save(order);
    }

    @Override
    public void deleteOrder(Long orderId) throws OrderException {
        Order order = findOrderById(orderId);
        orderRepository.delete(order);
    }

    @Override
    public Order cancelOrder(Long orderId, User user) throws OrderException {
        Order order = findOrderById(orderId);

        // Verify user owns the order
        if (!order.getUser().getId().equals(user.getId())) {
            throw new OrderException("You cannot cancel this order: " + orderId);
        }

        // Only allow cancellation if order is not already delivered
        if (order.getOrderStatus() == OrderStatus.DELIVERED) {
            throw new OrderException("Cannot cancel an order that has been delivered");
        }

        order.setOrderStatus(OrderStatus.CANCELLED);
        return orderRepository.save(order);
    }
}