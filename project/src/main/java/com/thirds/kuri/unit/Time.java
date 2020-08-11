package com.thirds.kuri.unit;

import java.math.BigDecimal;
import java.math.MathContext;

public class Time {
    public final BigDecimal seconds;

    private Time(BigDecimal seconds) {
        this.seconds = seconds;
    }

    public static Time seconds(float quantity) {
        return new Time(new BigDecimal(quantity));
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
}
