package com.bigtask.Resource;

import com.bigtask.valueObjects.Money;

import java.math.BigDecimal;

public class Device extends Resource {
    int quantity;

    protected Device(String name, Money customHourlyRate, int quantity) {
        super(name, customHourlyRate);
        if (quantity < 0)
            throw new IllegalArgumentException("Quantity cannot be negative");
        this.quantity = quantity;
    }

    protected Device(String name, int quantity) {
        super(name);
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    protected Money baseRatePerHour() {
        BigDecimal hourRate = new  BigDecimal("120");
        return new Money(hourRate);
    }

    @Override
    public String describe() {
        return String.format("Device '%s' - Quantity: %d", getName(), quantity);
    }
}
