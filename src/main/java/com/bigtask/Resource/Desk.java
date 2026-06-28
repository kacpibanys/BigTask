package com.bigtask.Resource;

import com.bigtask.valueObjects.Money;

public class Desk extends Resource {
    public enum DeskType {HOT, FIXED};
    private String type;


    protected Desk(String name, Money customHourlyRate) {
        super(name, customHourlyRate);
    }

    protected Desk(String name) {
        super(name);
    }

    @Override
    protected Money baseRatePerHour() {
        return null;
    }

    @Override
    public String describe() {
        return "";
    }
}
