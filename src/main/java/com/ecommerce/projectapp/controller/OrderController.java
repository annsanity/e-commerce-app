package com.ecommerce.projectapp.controller;

import com.ecommerce.projectapp.domain.PaymentMethod;
import com.ecommerce.projectapp.exception.OrderException;
import com.ecommerce.projectapp.exception.SellerException;
import com.ecommerce.projectapp.exception.UserException;
import com.ecommerce.projectapp.model.*;
import com.ecommerce.projectapp.repository.PaymentOrderRepository;
import com.ecommerce.projectapp.response.PaymentLinkResponse;
import com.ecommerce.projectapp.service.*;
import com.stripe.exception.StripeException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/orders")
@AllArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final UserService userService;
    private final OrderItemService orderItemService;
    private final CartService cartService;
    private final PaymentService paymentService;
    private final PaymentOrderRepository paymentOrderRepository;
    private final SellerReportService sellerReportService;
    private final SellerService sellerService;

    @PostMapping()
    public ResponseEntity<PaymentLinkResponse> createOrderHandler(@RequestBody Address shippingAddress, @RequestParam PaymentMethod paymentMethod, @RequestHeader("Authorization") String jwt)
            throws UserException, StripeException {

        User user = userService.findUserProfileByJwt(jwt);
        Cart cart = cartService.findUserCart(user);
        Set<Order> orders = orderService.createOrder(user, shippingAddress, cart);

        PaymentOrder paymentOrder = paymentService.createOrder(user, orders);

        PaymentLinkResponse res = new PaymentLinkResponse();

        String paymentUrl = paymentService.createStripePaymentLink(user, paymentOrder.getAmount(), paymentOrder.getId());

        res.setPayment_link_url(paymentUrl);

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<List<Order>> usersOrderHistoryHandler(@RequestHeader("Authorization") String jwt) throws UserException{

        User user = userService.findUserProfileByJwt(jwt);
        List<Order> orders = orderService.usersOrderHistory(user.getId());

        return new ResponseEntity<>(orders,HttpStatus.ACCEPTED);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity< Order> getOrderById(@PathVariable Long orderId, @RequestHeader("Authorization") String jwt) throws OrderException, UserException{

        User user = userService.findUserProfileByJwt(jwt);
        Order orders = orderService.findOrderById(orderId);

        return new ResponseEntity<>(orders,HttpStatus.ACCEPTED);
    }

    @GetMapping("/item/{orderItemId}")
    public ResponseEntity<OrderItem> getOrderItemById(@PathVariable Long orderItemId, @RequestHeader("Authorization") String jwt) throws Exception {

        User user = userService.findUserProfileByJwt(jwt);
        OrderItem orderItem = orderItemService.getOrderItemById(orderItemId);

        return new ResponseEntity<>(orderItem,HttpStatus.ACCEPTED);
    }

    @PutMapping("/{orderId}/cancel")
    public ResponseEntity<Order> cancelOrder(@PathVariable Long orderId, @RequestHeader("Authorization") String jwt
    ) throws UserException, OrderException, SellerException {

        User user = userService.findUserProfileByJwt(jwt);
        Order order = orderService.cancelOrder(orderId,user);

        Seller seller = sellerService.getSellerById(order.getSellerId());
        SellerReport report = sellerReportService.getSellerReport(seller);

        report.setCanceledOrders(report.getCanceledOrders() + 1);
        report.setTotalRefunds(report.getTotalRefunds() + order.getTotalSellingPrice());
        sellerReportService.updateSellerReport(report);

        return ResponseEntity.ok(order);
    }

}
