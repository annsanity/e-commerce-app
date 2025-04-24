package com.ecommerce.projectapp.controller;

import com.ecommerce.projectapp.domain.OrderStatus;
import com.ecommerce.projectapp.domain.PaymentMethod;
import com.ecommerce.projectapp.domain.PaymentOrderStatus;
import com.ecommerce.projectapp.model.Order;
import com.ecommerce.projectapp.model.PaymentOrder;
import com.ecommerce.projectapp.model.User;
import com.ecommerce.projectapp.repository.CartItemRepository;
import com.ecommerce.projectapp.repository.CartRepository;
import com.ecommerce.projectapp.request.ChargeRequest;
import com.ecommerce.projectapp.response.ApiResponse;
import com.ecommerce.projectapp.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final UserService userService;
    private final PaymentService paymentService;
    private final OrderService orderService;
    private final TransactionService transactionService;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    @GetMapping("/verify/{paymentId}")
    public ResponseEntity<ApiResponse> paymentSuccessHandler(
            @PathVariable String paymentId,
            @RequestParam(required = false) String orderId,
            @RequestHeader("Authorization") String jwt) throws Exception {

        // Get user
        User user = userService.findUserProfileByJwt(jwt);

        // Get payment order by payment ID from Stripe
        PaymentOrder paymentOrder = paymentService.getPaymentOrderByPaymentId(paymentId);

        // Verify payment belongs to user
        if (!paymentOrder.getUser().getId().equals(user.getId())) {
            throw new Exception("Unauthorized payment verification attempt");
        }

        // Update payment order status
        paymentOrder.setStatus(PaymentOrderStatus.SUCCESS);

        // Update order status for each order in the payment
        for (Order order : paymentOrder.getOrders()) {
            order = orderService.updateOrderStatus(order.getId(), OrderStatus.PLACED);

            // Create transaction record
            transactionService.createTransaction(order);
        }

        // Clear user's cart
        cartRepository.findByUserId(user.getId())
                .getCartItems()
                .forEach(cartItem -> cartItemRepository.delete(cartItem));

        // Return success response
        ApiResponse res = new ApiResponse();
        res.setMessage("Payment successful");
        res.setStatus(true);

        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @GetMapping("/cancel/{paymentId}")
    public ResponseEntity<ApiResponse> paymentCancelHandler(
            @PathVariable String paymentId,
            @RequestHeader("Authorization") String jwt) throws Exception {

        // Get user
        User user = userService.findUserProfileByJwt(jwt);

        // Get payment order
        PaymentOrder paymentOrder = paymentService.getPaymentOrderByPaymentId(paymentId);

        // Verify payment belongs to user
        if (!paymentOrder.getUser().getId().equals(user.getId())) {
            throw new Exception("Unauthorized payment cancellation attempt");
        }

        // Update payment order status
        paymentOrder.setStatus(PaymentOrderStatus.FAILED);

        // Cancel all orders in the payment
        for (Order order : paymentOrder.getOrders()) {
            orderService.cancelOrder(order.getId(), user);
        }

        // Return response
        ApiResponse res = new ApiResponse();
        res.setMessage("Payment canceled");
        res.setStatus(true);

        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}