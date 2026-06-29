package com.bigtask.Booking;

public enum BookingStatus {
    PENDING, CONFIRMED, CANCELLED, COMPLETED;

    public void verifyTransitionTo(BookingStatus nextStatus) {
        boolean isValid = switch (this) {
            case PENDING -> nextStatus == CONFIRMED || nextStatus == CANCELLED;
            case CONFIRMED -> nextStatus == COMPLETED || nextStatus == CANCELLED;
            case CANCELLED, COMPLETED -> false;
        };

        if (!isValid) {
            throw new IllegalStateException(
                    String.format("Cannot transition to %s because it is not valid", this)
            );
        }
    }
}
