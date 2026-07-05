package com.bigtask.PricingPolicy;

import com.bigtask.Booking.Booking;
import com.bigtask.valueObjects.Money;


public interface PricingPolicy {
    Money price(Booking booking);
}
