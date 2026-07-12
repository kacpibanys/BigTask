package com.bigtask.Payment;

import com.bigtask.valueObjects.Money;

public abstract class Payment {
    private Money amount;
    private String paymentId;
    private PaymentStatus paymentStatus;

    public abstract void capture();

    public Payment(Money amount, String paymentId, PaymentStatus paymentStatus) {
        this.amount = amount;
        this.paymentId = paymentId;
        this.paymentStatus = paymentStatus;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }
    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}
