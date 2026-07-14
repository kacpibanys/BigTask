package com.bigtask.Booking;

import com.bigtask.PricingPolicy.PricingPolicy;
import com.bigtask.Repositories.BookingRepository;
import com.bigtask.Repositories.ResourceRepository;
import com.bigtask.Repositories.UserRepository;
import com.bigtask.Resource.Desk;
import com.bigtask.Resource.Device;
import com.bigtask.Resource.Resource;
import com.bigtask.Resource.Room;
import com.bigtask.User.User;
import com.bigtask.valueObjects.Money;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class BookingService {
    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;
    private final ResourceRepository resourceRepository;
    private PricingPolicy pricingPolicy;

    public BookingService(UserRepository userRepository, BookingRepository bookingRepository, ResourceRepository resourceRepository, PricingPolicy pricingPolicy) {
        this.userRepository = userRepository;
        this.bookingRepository = bookingRepository;
        this.resourceRepository = resourceRepository;
        this.pricingPolicy = pricingPolicy;
    }

    public Booking book(User user, Resource resource, LocalDateTime start, int duartionMinutes) {
        LocalDateTime end = start.plusMinutes(duartionMinutes);

        return this.book(user, resource, start, end);
    }

    public Booking book(User user, Resource resource, LocalDateTime start, LocalDateTime end) {
        if (!end.isAfter(start)) {
            throw new IllegalArgumentException("Start date must be after end date");
        }

        int collisionCounter = 0;
        List<Booking> allBookings = bookingRepository.findAll();
        for (Booking existingBooking : allBookings) {
            if (!existingBooking.getResource().getName().equals(resource.getName())) {
                System.out.println("git. referuje do tego samego");
                continue;
            }

            BookingStatus status = existingBooking.getStatus();

            if (status != BookingStatus.CONFIRMED && status != BookingStatus.PENDING) {
                continue;
            }

            boolean isColliding = start.isBefore(existingBooking.getEnd()) && existingBooking.getStart().isBefore(end);

            if (isColliding) {
                collisionCounter++;
            }

            if (resource instanceof Room || resource instanceof Desk) {
                if (collisionCounter > 0) {
                    throw new IllegalStateException("Already booked resource");
                }
            } else if (resource instanceof Device) {
                Device device = (Device) resource;

                if (collisionCounter >= device.getQuantity()) {
                    throw new IllegalStateException("All devices booked");
                }
            }
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String datePart = start.format(formatter);
        int counter = bookingRepository.findAll().size() + 1;
        String bookingId = "BK-" + datePart + "-" + counter;

        Booking newBooking = new Booking(bookingId,
                user,
                resource,
                start, end,
                BookingStatus.PENDING,
                null, null);

        Money calculatedPrice = this.pricingPolicy.price(newBooking);
        newBooking.setCalculatedPrice(calculatedPrice);

        bookingRepository.addBooking(newBooking);
        return newBooking;
    }
    private Booking checkBooking(String bookingId) {
        return bookingRepository.findById(bookingId).
                orElseThrow(() -> new IllegalArgumentException("Booking not found"));
    }

    public void confirm(String bookingId) {
        Booking booking = checkBooking(bookingId);
        if (booking.getStatus() != BookingStatus.PENDING){
            throw new IllegalStateException("Cannot confirm not pending booking");
        }
        booking.setStatus(BookingStatus.CONFIRMED);
    }
    public void cancel(String bookingId) {
        Booking booking = checkBooking(bookingId);
        if (booking.getStatus() == BookingStatus.COMPLETED) {
            throw new IllegalStateException("Cannot cancel not complete booking");
        }
        booking.setStatus(BookingStatus.CANCELLED);
    }

    public void complete(String bookingId) {
        Booking booking = checkBooking(bookingId);
        if (booking.getStatus() != BookingStatus.CONFIRMED) {
            throw new IllegalStateException("Cannot complete booking");
        }
        booking.setStatus(BookingStatus.COMPLETED);
    }

    public List<Booking> list(User userFilter, Resource resourceFilter, BookingStatus bookingStatusFilter) {
        List<Booking> allBookings = bookingRepository.findAll();
        return allBookings.stream()
                .filter(booking -> userFilter == null || booking.getUser().equals(userFilter))
                .filter(booking -> resourceFilter == null || booking.getResource().getName().equals(resourceFilter.getName()))
                .filter(booking -> bookingStatusFilter == null || booking.getStatus() == bookingStatusFilter)
                .collect(Collectors.toList());
    }

    public void setPricingPolicy(PricingPolicy pricingPolicy) {
        this.pricingPolicy = pricingPolicy;
    }
}
