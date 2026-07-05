package com.bigtask.Payment;

import com.bigtask.valueObjects.Money;

public abstract class Payment {
    private Money amount;
    private String paymentId;
    private PaymentStatus paymentStatus;

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

    public Money getAmount() {
        return amount;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }
}
