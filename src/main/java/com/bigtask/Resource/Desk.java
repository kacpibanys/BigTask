package com.bigtask.Resource;

import com.bigtask.valueObjects.Money;

import java.math.BigDecimal;

public class Desk extends Resource {
    private static final BigDecimal RATE_FIXED = new BigDecimal("100");
    private static final BigDecimal RATE_HOT = new BigDecimal("120");

    private DeskEnum type;

    public Desk(String name, Money customHourlyRate, DeskEnum type) {
        super(name, customHourlyRate);
        this.type = type;
    }

    protected Desk(String name, DeskEnum type) {
        super(name);
        this.type = type;
    }

    @Override
    protected Money baseRatePerHour() {
        BigDecimal hourRate = switch (this.type) {
            case FIXED -> RATE_FIXED;
            case HOT -> RATE_HOT;
        };
        return new Money(hourRate);
    }

    @Override
    public String describe() {
        return String.format("Desk '%s' - Type: %s", getName(), getType());
    }


    public DeskEnum getType() {
        return type;
    }
}