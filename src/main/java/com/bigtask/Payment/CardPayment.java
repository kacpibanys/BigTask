package com.bigtask.Payment;

import com.bigtask.valueObjects.Money;

public class CardPayment extends Payment {
    private String last4;

    public CardPayment(Money amount, String paymentId, PaymentStatus paymentStatus, String last4) {
        super(amount, paymentId, paymentStatus);
        if (last4 == null || !last4.matches("\\d{4}")) {
            throw new IllegalArgumentException("Parameter needs to be 4 digits long");
        }

        this.last4 = last4;
    }

    @Override
    public void capture() {
        if (this.getPaymentStatus() != PaymentStatus.INITIATED) {
            throw new IllegalStateException("Payment needs to be initiated.");
        }
        this.setPaymentStatus(PaymentStatus.CAPTURED);
    }
}
