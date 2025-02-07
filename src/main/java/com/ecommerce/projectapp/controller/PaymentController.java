package com.ecommerce.projectapp.controller;

import com.ecommerce.projectapp.domain.PaymentMethod;
import com.ecommerce.projectapp.model.PaymentOrder;
import com.ecommerce.projectapp.model.User;
import com.ecommerce.projectapp.repository.CartItemRepository;
import com.ecommerce.projectapp.repository.CartRepository;
import com.ecommerce.projectapp.request.ChargeRequest;
import com.ecommerce.projectapp.response.ApiResponse;
import com.ecommerce.projectapp.response.PaymentLinkResponse;
import com.ecommerce.projectapp.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class PaymentController {

    private final UserService userService;
    private final PaymentService paymentService;
    private final TransactionService transactionService;
    private final SellerReportService sellerReportService;
    private final SellerService sellerService;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    @PostMapping("/api/payment/{paymentMethod}/order/{orderId}")
    public ResponseEntity<PaymentLinkResponse> paymentHandler(@PathVariable PaymentMethod paymentMethod, @PathVariable Long orderId,
            @RequestHeader("Authorization") String jwt) throws Exception {

        User user = userService.findUserProfileByJwt(jwt);
        PaymentLinkResponse paymentResponse;
        PaymentOrder order = paymentService.getPaymentOrderById(orderId);

        return new ResponseEntity<>(null, HttpStatus.CREATED);
    }

    @GetMapping("/api/payment/{paymentId}")
    public ResponseEntity<ApiResponse> paymentSuccessHandler(@PathVariable String paymentId, @RequestParam String paymentLinkId, @RequestHeader("Authorization") String jwt) throws Exception {

        User user = userService.findUserProfileByJwt(jwt);

        PaymentLinkResponse paymentResponse;

        PaymentOrder paymentOrder = paymentService.getPaymentOrderByPaymentId(paymentLinkId);

//        ChargeRequest chargeRequest = new ChargeRequest(paymentOrder.)
//        boolean paymentSuccess = paymentService.proceedPaymentOrder(chargeRequest);
//
//        if(paymentSuccess){
//            for(Order order:paymentOrder.getOrders()){
//                transactionService.createTransaction(order);
//                Seller seller=sellerService.getSellerById(order.getSellerId());
//                SellerReport report=sellerReportService.getSellerReport(seller);
//                report.setTotalOrders(report.getTotalOrders()+1);
//                report.setTotalEarnings(report.getTotalEarnings()+order.getTotalSellingPrice());
//                report.setTotalSales(report.getTotalSales()+order.getOrderItems().size());
//                sellerReportService.updateSellerReport(report);
//            }
//            Cart cart=cartRepository.findByUserId(user.getId());
//            cart.setCouponPrice(0);
//            cart.setCouponCode(null);
////        Set<CartItem> items=cart.getCartItems();
////        cartItemRepository.deleteAll(items);
////        cart.setCartItems(new HashSet<>());
//            cartRepository.save(cart);
//
//        }

        ApiResponse res = new ApiResponse();
        res.setMessage("Payment successful");
        res.setStatus(true);

        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

}
