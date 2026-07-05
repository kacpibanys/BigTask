package com.bigtask.Payment;

import com.bigtask.valueObjects.Money;

public class CardPayment extends Payment {
    String last4;

    public CardPayment(Money amount, String paymentId, PaymentStatus paymentStatus, String last4) {
        super(amount, paymentId, paymentStatus);
        if(last4.length()!=4){
            throw new IllegalArgumentException("last4 must be 4 digits long");
        }
        this.last4 = last4;
    }

    public String getLast4() {
        return last4;
    }
}
