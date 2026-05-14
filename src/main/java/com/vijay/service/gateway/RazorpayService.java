package com.vijay.service.gateway;

import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.vijay.domain.PaymentType;
import com.vijay.modal.Payment;
import com.vijay.modal.User;
import com.vijay.payload.response.PaymentLinkResponse;
import lombok.RequiredArgsConstructor;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RazorpayService {

    @Value("${razorpay.key.id:}")
    private String razorpayKeyId;

    @Value("${razorpay.key.secret:}")
    private String razorpayKeySecret;

    @Value("${razorpay.callback.base-url:http://localhost:5173}")
    private String callbackBaseUrl;



    public PaymentLinkResponse createPaymentLink(User user, Payment payment){


        try{

            RazorpayClient razorpayClient = new RazorpayClient(razorpayKeyId, razorpayKeySecret);

            Long amountInPaise = payment.getAmount() *(new java.math.BigDecimal("100")).intValue();

            JSONObject paymentLinkRequest = new JSONObject();
            paymentLinkRequest.put("amount", amountInPaise);
            paymentLinkRequest.put("currency", "INR");
            paymentLinkRequest.put("description", payment.getAmount());

            JSONObject customer = new JSONObject();
            customer.put("name", user.getFullName());
            customer.put("email", user.getFullName());
            if(user.getPhone() != null){
                customer.put("contact", user.getPhone());
            }

            paymentLinkRequest.put("customer", customer);

            JSONObject notify = new JSONObject();
            notify.put("email", true);
            notify.put("sms", user.getPhone() != null);
            paymentLinkRequest.put("notify", notify);

            //enable remainders
            paymentLinkRequest.put("remainder_enable", true);

            String successUrl = callbackBaseUrl + "/payment-success/" + payment.getId();

            paymentLinkRequest.put("callback_url", successUrl);
            paymentLinkRequest.put("callback_method", "get");

            JSONObject notes = new JSONObject();
            notes.put("user_id", user.getId());
            notes.put("payment_id", payment.getId());

            if(payment.getPaymentType() == PaymentType.MEMBERSHIP){
                notes.put("subscription_id", payment.getSubscription().getId());
                notes.put("plan", payment.getSubscription().getPlan().getPlanCode());
                notes.put("type", PaymentType.MEMBERSHIP);

            } else if(payment.getPaymentType() == PaymentType.FINE){
                // todo (later)
              //  notes.put("fine_id", payment.getFine().getId());
                notes.put("type", PaymentType.FINE);
            }

            paymentLinkRequest.put("notes", notes);

            PaymentLink paymentLink = razorpayClient.paymentLink.create(paymentLinkRequest);

            String paymentUrl = paymentLink.get("short_url");
            String paymentLinkId = paymentLink.get("id");

            PaymentLinkResponse response = new PaymentLinkResponse();
            response.setPayment_link_url(paymentUrl);
            response.setPayment_link_id(paymentLinkId);

            return response;

        }catch (RazorpayException e){
            throw new RuntimeException(e);

        }

    }

    public JSONObject fetchPaymentDetails(String paymentId) throws Exception {

        try {

            RazorpayClient razorpay = new RazorpayClient(razorpayKeyId, razorpayKeySecret);
            com.razorpay.Payment payment = razorpay.payments.fetch(paymentId);

            return payment.toJson();

        } catch (RazorpayException e) {

            throw new Exception("Failed to fetch payment details: " + e.getMessage(), e);
        }
    }

    public boolean isValidPayment(String paymentId) {
        try {

            JSONObject paymentDetails = fetchPaymentDetails(paymentId);
            String status = paymentDetails.optString("status");
            long amount = paymentDetails.optLong("amount");
            long amountInRupees = amount / 100;

            JSONObject notes = paymentDetails.getJSONObject("notes");

            String paymentType = notes.optString("type");

            if ("captured".equals(status)
                    || "authorized".equals(status)) {

                if ("MEMBERSHIP".equals(paymentType)) {

                    Long subscriptionId =
                            notes.optLong("subscription_id");

                    return subscriptionId != null
                            && amountInRupees > 0;
                }

                else if ("FINE".equals(paymentType)) {

                    Long fineId = notes.optLong("fine_id");

                    return fineId != null
                            && amountInRupees > 0;
                }
            }

        } catch (Exception e) {


        }

        return false;
    }
}
