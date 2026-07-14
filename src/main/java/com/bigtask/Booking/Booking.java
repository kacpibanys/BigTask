package com.bigtask.Booking;

import com.bigtask.Payment.Payment;
import com.bigtask.Resource.Resource;
import com.bigtask.User.User;
import com.bigtask.valueObjects.Money;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Booking {
    private final String id;
    private final User user;
    private final Resource resource;
    private final LocalDateTime start;
    private final LocalDateTime end;
    private BookingStatus status;
    private Money calculatedPrice;
    private Payment payment;


    public Booking(String id,
                   User user,
                   Resource resource,
                   LocalDateTime start, LocalDateTime end,
                   BookingStatus status,
                   Money calculatedPrice,
                   Payment payment) {
        if (!start.isBefore(end)) {
            throw new IllegalArgumentException("Start time must be before end time");
        }

        this.id = id;
        this.user = user;
        this.resource = resource;
        this.start = start;
        this.end = end;
        this.status = status;
        this.calculatedPrice = calculatedPrice;
        this.payment = payment;
    }

    public Money getHourlyRate() {
        return resource.hourlyRate();
    }

    public long getDurationInMinutes() {
        return Duration.between(this.start, this.end).toMinutes();
    }

    public LocalDateTime getStart() {
        return start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public String getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Money getCalculatedPrice() {
        return calculatedPrice;
    }

    public Resource getResource() {
        return resource;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public void setCalculatedPrice(Money calculatedPrice) {
        this.calculatedPrice = calculatedPrice;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }
}
