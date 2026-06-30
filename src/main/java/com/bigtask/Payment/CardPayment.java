package com.bigtask.Payment;

import com.bigtask.valueObjects.Money;

public class CardPayment extends Payment {
    String last4;

    public CardPayment(Money amount, String paymentId, PaymentStatus paymentStatus, String last4) {
        super(amount, paymentId, paymentStatus);
        this.last4 = last4.substring(last4.length() - 3);
    }

}
