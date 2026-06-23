package com.bigtask.Resource;

import com.bigtask.valueObjects.Money;

public abstract class Resource {
    private final String name;
    private Money customHourlyRate;

    protected Resource(String name, Money customHourlyRate) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Resource name cannot be null or empty");
        }
        this.name = name;
        this.customHourlyRate = customHourlyRate;
    }

    protected Resource(String name) {
        this(name, null);
    }

    protected abstract Money baseRatePerHour();
    public abstract String describe();

    public Money hourlyRate() {
        if (customHourlyRate != null) {
            return customHourlyRate;
        }else  {
            return baseRatePerHour();
        }
    }

    public String getName() {
        return name;
    }


}
