package com.thirds.kuri.unit;

public class AbsoluteTime {
    private final Time sinceFirstColony;

    private AbsoluteTime(Time sinceFirstColony) {
        this.sinceFirstColony = sinceFirstColony;
    }

    public static AbsoluteTime sinceFirstColony(Time time) {
        return new AbsoluteTime(time);
    }

    public Time getSinceFirstColony() {
        return sinceFirstColony;
    }

    public AbsoluteTime add(Time offset) {
        return new AbsoluteTime(sinceFirstColony.add(offset));
    }

    @Override
    public String toString() {
        return sinceFirstColony.asFormattedTime().toString();
    }
}
