package com.thirds.kuri.unit;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Length {
    private static final Length ASTRONOMICAL_UNIT = new Length(new BigDecimal("149 600 000 000".replace(" ", "")));
    private final BigDecimal metres;

    private Length(BigDecimal metres) {
        this.metres = metres;
    }

    public static Length solarRadii(float quantity) {
        return new Length(new BigDecimal("695 700 000".replace(" ", "")).multiply(new BigDecimal(quantity)));
    }

    public static Length earthRadii(float quantity) {
        return new Length(new BigDecimal("6 371 000".replace(" ", "")).multiply(new BigDecimal(quantity)));
    }

    public static Length astronomicalUnits(float quantity) {
        return new Length(ASTRONOMICAL_UNIT.metres.multiply(new BigDecimal(quantity)));
    }

    public BigDecimal asMetres() {
        return metres;
    }

    public BigDecimal asAstronomicalUnits() {
        return metres.divide(ASTRONOMICAL_UNIT.metres, 10, RoundingMode.HALF_EVEN);
    }
}
