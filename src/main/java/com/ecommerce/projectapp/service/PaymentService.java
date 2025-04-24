package com.ecommerce.projectapp.service;

import com.ecommerce.projectapp.model.Order;
import com.ecommerce.projectapp.model.PaymentOrder;
import com.ecommerce.projectapp.model.User;
import com.ecommerce.projectapp.request.ChargeRequest;
import com.stripe.exception.CardException;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;

public interface PaymentService {

    PaymentOrder createOrder(User user, Order order);

    PaymentOrder getPaymentOrderById(Long id) throws Exception;

    PaymentOrder getPaymentOrderByPaymentId(String paymentId) throws Exception;

    Charge proceedPaymentOrder(ChargeRequest request) throws CardException;

    String createStripePaymentLink(User user, Long amount, Long orderId) throws StripeException;
}