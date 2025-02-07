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
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CartService cartService;
    private final AddressRepository addressRepository;
    private final UserRepository userRepository;
    private final OrderItemService orderItemService;
    private final OrderItemRepository orderItemRepository;

    public Set<Order> createOrder(User user, Address shipAddress, Cart cart) {
        // Save shipping address if new
        if (!user.getAddresses().contains(shipAddress)) {
            user.getAddresses().add(shipAddress);
        }
        Address address = addressRepository.save(shipAddress);

        // Group cart items by seller
        Map<Long, List<CartItem>> itemsBySeller = cart.getCartItems().stream()
                .collect(Collectors.groupingBy(item -> item.getProduct().getSeller().getId()));

        Set<Order> orders = new HashSet<>();

        itemsBySeller.forEach((sellerId, cartItems) -> {
            // Calculate totals
            int totalOrderPrice = cartItems.stream().mapToInt(CartItem::getSellingPrice).sum();
            int totalItems = cartItems.stream().mapToInt(CartItem::getQuantity).sum();

            // Create and save order
            Order order = createOrder(user, sellerId, totalOrderPrice, totalItems, address);
            Order savedOrder = orderRepository.save(order);
            orders.add(savedOrder);

            // Create and save order items
            cartItems.forEach(item -> {
                OrderItem orderItem = createOrderItem(item, savedOrder);
                orderItemRepository.save(orderItem);
            });
        });

        return orders;
    }

    private Order createOrder(User user, Long sellerId, int totalPrice, int totalItems, Address address) {
        Order order = new Order();
        order.setUser(user);
        order.setSellerId(sellerId);
        order.setTotalMrpPrice(totalPrice);
        order.setTotalSellingPrice(totalPrice);
        order.setTotalItem(totalItems);
        order.setShippingAddress(address);
        order.setOrderStatus(OrderStatus.PENDING);
        order.getPaymentDetails().setStatus(PaymentStatus.PENDING);
        return order;
    }

    private OrderItem createOrderItem(CartItem item, Order order) {
        OrderItem orderItem = new OrderItem();
        orderItem.setOrder(order);
        orderItem.setMrpPrice(item.getMrpPrice());
        orderItem.setProduct(item.getProduct());
        orderItem.setQuantity(item.getQuantity());
        orderItem.setSize(item.getSize());
        orderItem.setUserId(item.getUserId());
        orderItem.setSellingPrice(item.getSellingPrice());
        order.getOrderItems().add(orderItem);
        return orderItem;
    }


    @Override
    public Order findOrderById(Long orderId) throws OrderException {

        Optional<Order> optional = orderRepository.findById(orderId);

        if(optional.isPresent()) {
            return optional.get();
        }
        throw new OrderException("Order not exist!");
    }

    @Override
    public List<Order> usersOrderHistory(Long userId) {

        return orderRepository.findByUserId(userId);
    }

    @Override
    public List<Order> getShopsOrders(Long sellerId) {

        return orderRepository.findBySellerIdOrderByOrderDateDesc(sellerId);
    }

    @Override
    public Order updateOrderStatus(Long orderId, OrderStatus orderStatus) throws OrderException {

        Order order=findOrderById(orderId);
        order.setOrderStatus(orderStatus);
        return orderRepository.save(order);
    }

    @Override
    public void deleteOrder(Long orderId) throws OrderException {

        orderRepository.deleteById(orderId);
    }

    @Override
    public Order cancelOrder(Long orderId, User user) throws OrderException {

        Order order = this.findOrderById(orderId);
        if(user.getId() != order.getUser().getId()){
            throw new OrderException("you can't perform this action "+ orderId);
        }
        order.setOrderStatus(OrderStatus.CANCELLED);

        return orderRepository.save(order);
    }
}