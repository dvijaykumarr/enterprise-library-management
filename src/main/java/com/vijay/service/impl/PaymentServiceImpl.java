package com.vijay.service.impl;

import com.vijay.domain.PaymentStatus;
import com.vijay.modal.Payment;
import com.vijay.modal.Subscription;
import com.vijay.modal.User;
import com.vijay.payload.dto.PaymentDTO;
import com.vijay.payload.request.PaymentInitiateRequest;
import com.vijay.payload.request.PaymentVerifyRequest;
import com.vijay.payload.response.PaymentInitiateResponse;
import com.vijay.repository.PaymentRepository;
import com.vijay.repository.SubscriptionRepository;
import com.vijay.repository.UserRepository;
import com.vijay.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {


    private final UserRepository userRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final PaymentRepository paymentRepository;

    @Override
    public PaymentInitiateResponse initiatePayment(PaymentInitiateRequest request) throws Exception {

        User user = userRepository.findById(request.getUserId()).get();

        Payment payment = new Payment();
        payment.setUser(user);
        payment.setPaymentType(request.getPaymentType());
        payment.setGateway(request.getGateway());
        payment.setAmount(request.getAmount());
        payment.setDescription(request.getDescription());
        payment.setStatus(PaymentStatus.PENDING);
        payment.setTransactionId("TXN_" + UUID.randomUUID());
        payment.setInitiatedAt(LocalDateTime.now());

        if(request.getSubscriptionId() != null){
            Subscription sub = subscriptionRepository
                    .findById(request.getSubscriptionId())
                    .orElseThrow(()->new Exception("Subscription not found"));
            payment.setSubscription(sub);
        }
        payment = paymentRepository.save(payment);
        return null;
    }

    @Override
    public PaymentDTO verifyPayment(PaymentVerifyRequest req) {
        return null;
    }

    @Override
    public Page<PaymentDTO> getAllPayments(Pageable pageable) {
        return null;
    }
}
