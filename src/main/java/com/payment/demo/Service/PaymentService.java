package com.payment.demo.Service;

import com.payment.demo.Entity.Payment;
import com.payment.demo.Payload.PaymentRequest;
import com.payment.demo.Repository.PaymentRepository;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Service
public class PaymentService {
    @Value("${stripe.secretKey}")
    private String stripeSecretKey;

    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @PostConstruct
    public void init() {
        Stripe.apiKey = stripeSecretKey;
    }

    public PaymentIntent createPaymentIntent(PaymentRequest paymentRequest) throws StripeException {
        Map<String, Object> params = new HashMap<>();
        params.put("amount", paymentRequest.getAmount());
        params.put("currency", paymentRequest.getCurrency());
        // Add additional parameters as needed

        PaymentIntent paymentIntent = PaymentIntent.create(params);


        Payment covertedPayment = mapToEntity(paymentRequest);

        Payment savePayment = paymentRepository.save(covertedPayment);
        PaymentRequest dto = maptodto(savePayment);

        return paymentIntent;
    }
    private Payment mapToEntity (PaymentRequest dto){

        Payment pm = new Payment();

        pm.setAmount(dto.getAmount());
        pm.setCurrency(dto.getCurrency());

        return pm;
    }
    private PaymentRequest maptodto (Payment payment){

        PaymentRequest ur = new PaymentRequest();

       ur.setAmount(payment.getAmount());
       ur.setCurrency(payment.getCurrency());

        return ur;
    }
}
