package com.bigtask.Resource;

import com.bigtask.valueObjects.Money;

import java.math.BigDecimal;
import java.util.Set;

public class Room extends Resource {
    private int seats;
    private Set<String> equipment;

    public Room(String name, Money customHourlyRate, int seats, Set<String> equipment) {
        super(name, customHourlyRate);

        this.seats = seats;
        this.equipment = equipment;
    }

    public Room(String name, int seats, Set<String> equipment) {
        super(name);

        this.seats = seats;
        this.equipment = equipment;
    }



    @Override
    protected Money baseRatePerHour() {
        //return null
        BigDecimal hourRate = new  BigDecimal("120");
        return new Money(hourRate);
    }

    @Override
    public String describe() {
        return String.format("Room '%s' - Seats: %d, Equipment: %s", getName(), seats, String.join(", ", equipment));
    }

    public int getSeats() {
        return seats;
    }
    public Set<String> getEquipment() {
        return equipment;
    }
}
