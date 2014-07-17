package com.dsh105.cooldown;

public enum TimeUnit {

    MILLISECONDS(1),
    SECONDS(1000),
    MINUTES(60000),
    HOURS(3600000);

    private int multiplier;

    TimeUnit(int multiplier) {
        this.multiplier = multiplier;
    }

    public long toMilliseconds(long time) {
        return time * multiplier;
    }

    public long getTime(long milliseconds) {
        return milliseconds / multiplier;
    }
}