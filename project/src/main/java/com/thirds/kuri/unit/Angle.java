package com.thirds.kuri.unit;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Angle {
    private final BigDecimal radians;

    private Angle(BigDecimal radians) {
        this.radians = radians;
    }

    public static Angle radians(float quantity) {
        return new Angle(new BigDecimal(quantity));
    }

    public BigDecimal asRadians() {
        return radians;
    }
}
