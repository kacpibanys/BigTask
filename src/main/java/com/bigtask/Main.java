package com.bigtask;

import com.bigtask.Booking.Booking;
import com.bigtask.Booking.BookingStatus;
import com.bigtask.PricingPolicy.PricingPolicy;
import com.bigtask.PricingPolicy.StandardPricing;
import com.bigtask.Resource.Resource;
import com.bigtask.User.User;
import com.bigtask.valueObjects.Money;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static com.bigtask.Booking.BookingStatus.PENDING;

public class Main  {
    public static void main(String[] args) {
        /*User user = new User("LAMA", "email");

        LocalDateTime startTime = LocalDateTime.of(2026, 1, 12, 10, 0);
        LocalDateTime endTime = LocalDateTime.of(2026, 1, 13, 12, 0);
        Money money = new Money(new BigDecimal("1000.00") );
        Resource resource = new Resource("resource", money) {
            @Override
            protected Money baseRatePerHour() {
                return null;
            }

            @Override
            public String describe() {
                return "";
            }
        };*/
        //BookingStatus bookingStatus = PENDING;
        //Booking newBooking = new Booking("id", user, resource , startTime, endTime, bookingStatus,  money, null);
        //System.out.println(newBooking.getHourlyRate().getAmount());
        //Money hourlyRate = newBooking.getHourlyRate();
        //System.out.println(hourlyRate.getAmount());
        //Money newMoney = new Money(new BigDecimal("10.00") );
        //Money base = new PricingPolicy.price(newBooking);

        //System.out.println(PricingPolicy.price(newBooking).getAmount());
    }
}
