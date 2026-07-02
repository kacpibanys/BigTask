package com.bigtask.PricingPolicy;

import com.bigtask.Booking.Booking;
import com.bigtask.valueObjects.Money;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

public class HappyHoursPricing implements PricingPolicy {
    @Override
    public Money price(Booking booking) {
        Money hourlyRateMoney = booking.getHourlyRate();
        BigDecimal hourlyRate = hourlyRateMoney.getAmount();

        BigDecimal pricePerMinute = hourlyRate.divide(new BigDecimal("60"), 10, RoundingMode.HALF_UP);
        long minutes = booking.getDurationInMinutes();
        BigDecimal totalPrice = pricePerMinute.multiply(new BigDecimal(minutes));
        LocalDateTime start = booking.getStart();

        int hour = start.getHour();

        if (hour >= 14 && hour < 16) {
            BigDecimal discountMultiplier = new BigDecimal("0.70");
            totalPrice = totalPrice.multiply(discountMultiplier);
        }

        totalPrice = totalPrice.setScale(2, RoundingMode.HALF_UP);

        return new Money(totalPrice);
    }

}