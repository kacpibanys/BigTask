package com.bigtask.Invoice;

import com.bigtask.Booking.Booking;
import com.bigtask.User.User;
import com.bigtask.valueObjects.Money;

import java.time.LocalDateTime;

public class Invoice implements Billable{
    private String invoiceNumber;
    private LocalDateTime issueDate;
    private User buyer;
    private Money total;
    private String itemDescription;

    public Invoice(String invoiceNumber, LocalDateTime issuedDate, User buyer, Money total, String itemDescription) {
        this.invoiceNumber = invoiceNumber;
        this.issueDate = issuedDate;
        this.buyer = buyer;
        this.total = total;
        this.itemDescription = itemDescription;
    }

    @Override
    public Invoice toInvoice(Booking booking) {
        String invoiceNumber = "FV/" + booking.getId();

        LocalDateTime issuedDate = LocalDateTime.now();
        User buyer = booking.getUser();
        Money total = booking.getCalculatedPrice();
        String description = String.format("Rezerwacja %s %s-%s",
                booking.getResource().getName(),
                booking.getStart().toString(),
                booking.getEnd().toString()
        );
        return new Invoice(invoiceNumber, issuedDate, buyer, total, description);
    }

    @Override
    public String toString() {
        String buyerInfo = (buyer != null) ? buyer.getEmail() : "Unknown";
        String totalInfo = (total != null) ? total.toString() : "0.00";

        return String.format("Invoice No: %s | Date: %s | Buyer: %s | Total: %s | Desc: %s",
                invoiceNumber,
                issueDate,
                buyerInfo,
                totalInfo,
                itemDescription
        );
    }
}
