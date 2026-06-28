package com.bigtask.Resource;

import com.bigtask.valueObjects.Money;
import com.bigtask.Resource.DeskEnum;

import java.math.BigDecimal;

public class Desk extends Resource {
    private DeskEnum type;

    protected Desk(String name, Money customHourlyRate, DeskEnum type) {

        super(name, customHourlyRate);
        this.type = type;
    }

    protected Desk(String name, DeskEnum type) {

        super(name);
        this.type = type;
    }

    @Override
    protected Money baseRatePerHour() {

        BigDecimal hourRate = new  BigDecimal("120");
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
