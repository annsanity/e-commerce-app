package com.ecommerce.projectapp.controller;

import com.ecommerce.projectapp.domain.PaymentMethod;
import com.ecommerce.projectapp.exception.OrderException;
import com.ecommerce.projectapp.exception.UserException;
import com.ecommerce.projectapp.model.*;
import com.ecommerce.projectapp.repository.PaymentOrderRepository;
import com.ecommerce.projectapp.response.PaymentLinkResponse;
import com.ecommerce.projectapp.service.*;
import com.stripe.exception.StripeException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final UserService userService;
    private final OrderItemService orderItemService;
    private final CartService cartService;
    private final PaymentService paymentService;
    private final PaymentOrderRepository paymentOrderRepository;

    @PostMapping()
    public ResponseEntity<PaymentLinkResponse> createOrderHandler(
            @RequestBody Address shippingAddress,
            @RequestParam PaymentMethod paymentMethod,
            @RequestHeader("Authorization") String jwt) throws UserException, StripeException {

        // Get user and cart
        User user = userService.findUserProfileByJwt(jwt);
        Cart cart = cartService.findUserCart(user);

        // Create order
        Order order = orderService.createOrder(user, shippingAddress, cart);

        // Create payment order
        PaymentOrder paymentOrder = paymentService.createOrder(user, order);

        // Generate payment link
        String paymentUrl = paymentService.createStripePaymentLink(user, paymentOrder.getAmount(), paymentOrder.getId());

        // Build response
        PaymentLinkResponse res = new PaymentLinkResponse();
        res.setPayment_link_url(paymentUrl);
        res.setPayment_link_id(paymentOrder.getPaymentLinkId());

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<List<Order>> usersOrderHistoryHandler(@RequestHeader("Authorization") String jwt) throws UserException {
        User user = userService.findUserProfileByJwt(jwt);
        List<Order> orders = orderService.usersOrderHistory(user.getId());
        return new ResponseEntity<>(orders, HttpStatus.ACCEPTED);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long orderId, @RequestHeader("Authorization") String jwt)
            throws OrderException, UserException {
        User user = userService.findUserProfileByJwt(jwt);
        Order order = orderService.findOrderById(orderId);

        // Security check - only allow users to view their own orders
        if (!order.getUser().getId().equals(user.getId())) {
            throw new OrderException("Not authorized to view this order");
        }

        return new ResponseEntity<>(order, HttpStatus.ACCEPTED);
    }

    @GetMapping("/item/{orderItemId}")
    public ResponseEntity<OrderItem> getOrderItemById(@PathVariable Long orderItemId, @RequestHeader("Authorization") String jwt)
            throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        OrderItem orderItem = orderItemService.getOrderItemById(orderItemId);

        // Security check - only allow users to view their own order items
        if (!orderItem.getUserId().equals(user.getId())) {
            throw new OrderException("Not authorized to view this order item");
        }

        return new ResponseEntity<>(orderItem, HttpStatus.ACCEPTED);
    }

    @PutMapping("/{orderId}/cancel")
    public ResponseEntity<Order> cancelOrder(@PathVariable Long orderId, @RequestHeader("Authorization") String jwt)
            throws UserException, OrderException {
        User user = userService.findUserProfileByJwt(jwt);
        Order order = orderService.cancelOrder(orderId, user);
        return ResponseEntity.ok(order);
    }
}