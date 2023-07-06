package com.payment.demo.Payload;

import lombok.Data;

@Data
public class PaymentRequest {
    private String amount;
    private String currency;

}
