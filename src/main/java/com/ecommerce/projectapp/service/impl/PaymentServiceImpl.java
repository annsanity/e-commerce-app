package com.ecommerce.projectapp.service.impl;

import com.ecommerce.projectapp.domain.PaymentOrderStatus;
import com.ecommerce.projectapp.model.Order;
import com.ecommerce.projectapp.model.PaymentOrder;
import com.ecommerce.projectapp.model.User;
import com.ecommerce.projectapp.repository.CartRepository;
import com.ecommerce.projectapp.repository.OrderRepository;
import com.ecommerce.projectapp.repository.PaymentOrderRepository;
import com.ecommerce.projectapp.request.ChargeRequest;
import com.ecommerce.projectapp.service.PaymentService;
import com.stripe.Stripe;
import com.stripe.exception.CardException;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    @Value("${stripe.api.key}")
    private String stripeSecretKey;

    private final PaymentOrderRepository paymentOrderRepository;
    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;

    @Override
    public PaymentOrder createOrder(User user, Order order) {
        Long amount = (long) order.getTotalSellingPrice();
        int couponPrice = cartRepository.findByUserId(user.getId()).getCouponPrice();

        PaymentOrder paymentOrder = new PaymentOrder();
        paymentOrder.setUser(user);
        paymentOrder.setAmount(amount - couponPrice);
        paymentOrder.setOrders(Collections.singleton(order));

        return paymentOrderRepository.save(paymentOrder);
    }

    @Override
    public PaymentOrder getPaymentOrderById(Long id) throws Exception {
        Optional<PaymentOrder> optionalPaymentOrder = paymentOrderRepository.findById(id);
        if (optionalPaymentOrder.isEmpty()) {
            throw new Exception("Payment Order Not Found With id " + id);
        }
        return optionalPaymentOrder.get();
    }

    @Override
    public PaymentOrder getPaymentOrderByPaymentId(String paymentLinkId) throws Exception {
        PaymentOrder paymentOrder = paymentOrderRepository.findByPaymentLinkId(paymentLinkId);
        if (paymentOrder == null) {
            throw new Exception("Payment Order Not Found With id " + paymentLinkId);
        }
        return paymentOrder;
    }

    @Override
    public Charge proceedPaymentOrder(ChargeRequest chargeRequest) throws CardException {
        // This would be implemented for direct Stripe charge API
        // But for our simplified approach, we're using Checkout Sessions
        return null;
    }

    @Override
    public String createStripePaymentLink(User user, Long amount, Long orderId) throws StripeException {
        // Set your Stripe API key
        Stripe.apiKey = stripeSecretKey;

        // Create Stripe Checkout Session
        SessionCreateParams params = SessionCreateParams.builder()
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("http://localhost:3000/payment-success?orderId=" + orderId)
                .setCancelUrl("http://localhost:3000/payment-cancel")
                .addLineItem(SessionCreateParams.LineItem.builder()
                        .setQuantity(1L)
                        .setPriceData(SessionCreateParams.LineItem.PriceData.builder()
                                .setCurrency("usd")
                                .setUnitAmount(amount * 100) // Amount in cents
                                .setProductData(SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                        .setName("Order #" + orderId)
                                        .setDescription("Purchase from VendorHub")
                                        .build())
                                .build())
                        .build())
                .build();

        // Create the Checkout Session
        Session session = Session.create(params);

        // Save the payment link ID to the payment order
        PaymentOrder paymentOrder;
        try {
            paymentOrder = getPaymentOrderById(orderId);
            paymentOrder.setPaymentLinkId(session.getId());
            paymentOrderRepository.save(paymentOrder);
        } catch (Exception e) {
            throw new StripeException("Failed to update payment order", null, null, null) {};
        }

        // Return the URL to redirect the customer to
        return session.getUrl();
    }
}