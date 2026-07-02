package com.bigtask.Repositories;

import com.bigtask.Booking.Booking;

import java.util.List;
import java.util.Optional;

public interface BookingRepository {
    void addBooking(Booking booking);
    Optional<Booking> findById(String id);
    List<Booking> findAll();
}
