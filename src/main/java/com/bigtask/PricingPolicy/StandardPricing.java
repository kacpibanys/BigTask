package com.bigtask.PricingPolicy;

import com.bigtask.Booking.Booking;
import com.bigtask.valueObjects.Money;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class StandardPricing implements PricingPolicy {
    @Override
    public Money price(Booking booking) {
        Money hourlyRateMoney = booking.getHourlyRate();

        BigDecimal hourlyRate = hourlyRateMoney.getAmount();

        BigDecimal pricePerMinute = hourlyRate.divide(new BigDecimal("60"),
                10, RoundingMode.HALF_UP);

        long minutes = booking.getDurationInMinutes();
        BigDecimal totalPrice = pricePerMinute.multiply(new BigDecimal(minutes));
        totalPrice = totalPrice.setScale(2, RoundingMode.HALF_UP);

        return new Money(totalPrice);
    }
}
