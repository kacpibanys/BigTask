package com.bigtask.Payment;

import com.bigtask.Booking.Booking;
import com.bigtask.Repositories.BookingRepository;

public class PaymentService {
    //private final String bookingId;
    //private String last4;
    private final BookingRepository bookingRepository;

    public PaymentService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    public Payment pay(String bookingId, String cardLast4){
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new IllegalArgumentException("Reservation " + bookingId + " not found"));

        if (booking.getCalculatedPrice() == null){
            throw new IllegalStateException("Reservation does not have a calculated price. Cannot pay");
        }

        CardPayment cardPayment = new CardPayment(
                booking.getCalculatedPrice(),
                bookingId,
                PaymentStatus.INITIATED,
                cardLast4
        );
        cardPayment.capture();
        booking.setPayment(cardPayment);

        return cardPayment;
    }
}