package com.bigtask.Resource;

import com.bigtask.valueObjects.Money;

import java.math.BigDecimal;
import java.util.Set;

public class Room extends Resource {
    private int seats;
    private Set<String> equipment;

    protected Room(String name, Money customHourlyRate, int seats, Set<String> equipment) {
        super(name, customHourlyRate);

        this.seats = seats;
        this.equipment = equipment;
    }

    protected Room(String name) {
        super(name);
    }

    @Override
    protected Money baseRatePerHour() {
        //return null
        BigDecimal hourRate = new  BigDecimal("120");
        return new Money(hourRate);
    }

    @Override
    public String describe() {
        return String.format("Sala '%s' - Miejsc: %d, Sprzęt: %s", getName(), seats, String.join(", ", equipment));
    }

    public int getSeats() {
        return seats;
    }
    public Set<String> getEquipment() {
        return equipment;
    }
}
