package com.vijay.service;

import com.vijay.payload.dto.PaymentDTO;
import com.vijay.payload.request.PaymentInitiateRequest;
import com.vijay.payload.request.PaymentVerifyRequest;
import com.vijay.payload.response.PaymentInitiateResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PaymentService {

    PaymentInitiateResponse initiatePayment(PaymentInitiateRequest request) throws Exception;

    PaymentDTO verifyPayment(PaymentVerifyRequest req);

    Page<PaymentDTO> getAllPayments(Pageable pageable);

}
