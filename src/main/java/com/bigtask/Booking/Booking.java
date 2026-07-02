package com.bigtask.Booking;

import com.bigtask.Resource.Resource;
import com.bigtask.User.User;
import com.bigtask.valueObjects.Money;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Booking {
    private String id;
    private User user;
    private Resource resource;
    private LocalDateTime start;
    private LocalDateTime end;
    private BookingStatus status;
    private Money calculatedPrice;
    //Payment payment;


    public Booking(String id, User user, Resource resource, LocalDateTime start, LocalDateTime end, BookingStatus status, Money calculatedPrice) {
        this.id = id;
        this.user = user;
        this.resource = resource;

        this.status = status;
        this.calculatedPrice = calculatedPrice;
        if (!start.isBefore(end)) {
            throw new IllegalArgumentException("Start time must be before end time");
        }else{
            this.start = start;
            this.end = end;
        }


    }

    public Money getHourlyRate(){
        return resource.hourlyRate();
    }

    public long getDurationInMinutes() {
        return Duration.between(this.start, this.end).toMinutes();
    }

    public LocalDateTime getStart() {
        return start;
    }

}
