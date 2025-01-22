package com.ecommerce.projectapp.model;

import com.ecommerce.projectapp.domain.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDetails {

    private String paymentId;
    private String razorpayPaymentLinkId;
    private String razorpayPaymentLinkStatus;
    private String razorPaymentId;
    private PaymentStatus status;
}
