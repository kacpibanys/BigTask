package com.bigtask.Invoice;

import com.bigtask.Booking.Booking;
import com.bigtask.User.User;
import com.bigtask.valueObjects.Money;

import java.time.LocalDateTime;

public class Invoice{
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

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public LocalDateTime getIssueDate() {
        return issueDate;
    }

    public User getBuyer() {
        return buyer;
    }

    public Money getTotal() {
        return total;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    @Override
    public String toString() {
        return "Invoice: " + invoiceNumber + " | for: " + buyer.getDisplayName() + " | amount: " + total + " | " + itemDescription;
    }


}
