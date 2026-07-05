package com.bigtask.Payment;

import com.bigtask.valueObjects.Money;

public abstract class Payment {
    Money amount;
    String paymentId;
    PaymentStatus paymentStatus;

    public void capture() {
        if (this.paymentStatus != PaymentStatus.INITIATED) {
            throw new IllegalStateException("Payment needs to be initiated.");
        }
        this.paymentStatus = PaymentStatus.CAPTURED;
    }

    public Payment(Money amount, String paymentId, PaymentStatus paymentStatus) {
        this.amount = amount;
        this.paymentId = paymentId;
        this.paymentStatus = paymentStatus;
    }
}
