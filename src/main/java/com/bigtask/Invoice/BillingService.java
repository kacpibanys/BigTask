package com.bigtask.Invoice;

import com.bigtask.Booking.Booking;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class BillingService implements Billable {
    private final List<Invoice> issuedInvoices = new ArrayList<>();

    @Override
    public Invoice toInvoice(Booking booking) {
        if (booking.getCalculatedPrice() == null) {
            throw new IllegalStateException("Cannot make the invoice without the price.");
        }

        LocalDateTime issueDateTime = LocalDateTime.now();
        LocalDate today = issueDateTime.toLocalDate();


        String datePart = today.format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        long todaysCount = issuedInvoices.stream()
                .filter(inv -> inv.getIssueDate().toLocalDate().equals(today))
                .count();
        long counter = todaysCount + 1;


        String invoiceNumber = "INV-" + datePart + "-" + counter;

        String description = String.format("Reservation %s %s-%s",
                booking.getResource().getName(),
                booking.getStart().toString(),
                booking.getEnd().toString()
        );

        Invoice invoice = new Invoice(
                invoiceNumber,
                issueDateTime,
                booking.getUser(),
                booking.getCalculatedPrice(),
                description
        );

        issuedInvoices.add(invoice);

        return invoice;
    }
}
