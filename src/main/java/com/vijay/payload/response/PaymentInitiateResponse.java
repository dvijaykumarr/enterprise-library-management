package com.vijay.payload.response;

import com.vijay.domain.PaymentGateway;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentInitiateResponse {

    private Long paymentId;
    private PaymentGateway gateway;

    private String transactionId;

    private String razorpayOrderId;

    private Long amount;

    private String description;

    //frontend should redirect user to this url for payment
    private String checkoutUrl;

    private String message;

    private Boolean success;

}
