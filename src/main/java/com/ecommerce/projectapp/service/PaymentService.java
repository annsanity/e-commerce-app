package com.ecommerce.projectapp.service;

import com.ecommerce.projectapp.model.Order;
import com.ecommerce.projectapp.model.PaymentOrder;
import com.ecommerce.projectapp.model.User;
import com.stripe.exception.StripeException;

import java.util.Set;

public interface PaymentService {

    PaymentOrder createOrder(User user, Set<Order> orders);

    PaymentOrder getPaymentOrderById(Long id) throws Exception;

    PaymentOrder getPaymentOrderByPaymentId(String paymentId) throws Exception;

    Boolean ProceedPaymentOrder (PaymentOrder paymentOrder, String paymentId, String paymentLinkId);

    String createStripePaymentLink(User user, Long Amount, Long orderId) throws StripeException;
}
}
