package com.thirds.kuri.unit;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class Time {
    public final BigDecimal seconds;

    private Time(BigDecimal seconds) {
        this.seconds = seconds;
    }

    public static Time seconds(float quantity) {
        return new Time(new BigDecimal(quantity));
    }

    public static Time minutes(float quantity) {
        return new Time(new BigDecimal(quantity).multiply(new BigDecimal("60")));
    }

    public static Time hours(float quantity) {
        return new Time(new BigDecimal(quantity).multiply(new BigDecimal("3600")));
    }

    public static Time days(float quantity) {
        return new Time(new BigDecimal(quantity).multiply(new BigDecimal("86400")));
    }

    public static Time years(float quantity) {
        return new Time(new BigDecimal(quantity).multiply(new BigDecimal("31557600")));
    }

    public static Time seconds(BigDecimal quantity) {
        return new Time(quantity);
    }

    public BigDecimal asSeconds() {
        return seconds;
    }

    public BigDecimal asMinutes() {
        return seconds.divide(new BigDecimal("60"), MathContext.DECIMAL128);
    }

    public BigDecimal asHours() {
        return seconds.divide(new BigDecimal("3600"), MathContext.DECIMAL128);
    }

    public BigDecimal asDays() {
        return seconds.divide(new BigDecimal("86400"), MathContext.DECIMAL128);
    }

    public BigDecimal asYears() {
        return seconds.divide(new BigDecimal("31557600"), MathContext.DECIMAL128);
    }

    public Time add(Time other) {
        return Time.seconds(seconds.add(other.seconds));
    }

    @Override
    public String toString() {
        // Select the most appropriate unit.
        if (asMinutes().compareTo(BigDecimal.ONE) <= 0) {
            return asSeconds().round(new MathContext(5)) + " s";

        } else if (asHours().compareTo(BigDecimal.ONE) <= 0) {
            return asMinutes().round(new MathContext(5)) + " mins";

        } else if (asDays().compareTo(BigDecimal.ONE) <= 0) {
            return asHours().round(new MathContext(5)) + " hrs";

        } else if (asYears().compareTo(BigDecimal.ONE) <= 0) {
            return asDays().round(new MathContext(5)) + " days";

        } else {
            return asYears().round(new MathContext(5)) + " years";
        }
    }

    public FormattedTime asFormattedTime() {
        BigDecimal[] seconds = asSeconds().divideAndRemainder(new BigDecimal("60"));
        BigDecimal[] minutes = seconds[0].divideAndRemainder(new BigDecimal("60"));
        BigDecimal[] hours = minutes[0].divideAndRemainder(new BigDecimal("24"));
        return new FormattedTime(
                hours[0].setScale(0, RoundingMode.HALF_UP).intValue(),
                hours[1].setScale(0, RoundingMode.HALF_UP).intValue(),
                minutes[1].setScale(0, RoundingMode.HALF_UP).intValue(),
                seconds[1].floatValue()
        );
    }
}
