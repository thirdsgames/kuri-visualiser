package com.thirds.kuri.unit;

public class FormattedTime {
    private final int day, hours, minutes;
    public final float seconds;

    public FormattedTime(int day, int hours, int minutes, float seconds) {
        this.day = day;
        this.hours = hours;
        this.minutes = minutes;
        this.seconds = seconds;
    }

    @Override
    public String toString() {
        return toIso8601();
    }

    /**
     * Returns an ISO 8601-like string suitable for rendering Kuri dates.
     */
    public String toIso8601() {
        return String.format("%05d %02d:%02d:%02d", day, hours, minutes, (int) seconds);
    }
}
