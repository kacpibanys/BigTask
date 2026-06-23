package com.bigtask.ValueObjects;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Money {
    private final BigDecimal amount;

    public Money(BigDecimal amount) {
        if (amount == null || amount.signum() < 0) {
            throw new IllegalArgumentException("Amount cannot be negative");
        }
        this.amount = amount;
        this.amount.setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal getAmount() {
        return amount;
    }


    static Money valueOf(String amount) {
        return new Money(new BigDecimal(amount));
    }

    /* Space for Money of  double */

    public Money add(Money other) {
        return new Money(this.amount.add(other.amount));
    }

    public Money subtract(Money other) {
        if (this.amount.compareTo(other.amount) < 0) {
            throw new IllegalArgumentException("You're trying to subtract bigger value than your amount!");
        }
        return new Money(this.amount.subtract(other.amount));
    }

    public Money multiply(BigDecimal multiplier) {
        return new Money(this.amount.multiply(multiplier));
    }

}
