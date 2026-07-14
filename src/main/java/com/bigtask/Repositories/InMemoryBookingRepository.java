package com.bigtask.Repositories;

import com.bigtask.Booking.Booking;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InMemoryBookingRepository implements BookingRepository {
    private final List<Booking> bookings = new ArrayList<>();

    @Override
    public void addBooking(Booking booking) {
        bookings.add(booking);
    }

    @Override
    public Optional<Booking> findById(String id) {
        for (Booking booking : bookings) {
            return bookings.stream().filter(b -> b.getId().equals(id)).findFirst();
        }
        return Optional.empty();
    }

    @Override
    public List<Booking> findAll() {
        return new ArrayList<>(bookings);
    }
}