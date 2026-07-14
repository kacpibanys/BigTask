package com.bigtask.Invoice;

import com.bigtask.Booking.Booking;

public interface Billable {
    Invoice toInvoice(Booking booking);
}
