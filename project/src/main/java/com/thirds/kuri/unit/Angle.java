package com.thirds.kuri.unit;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 * Ensures that the angle is always in the range 0-2pi.
 */
public class Angle {
    public static final BigDecimal TAU = new BigDecimal("6.28318531");

    private final BigDecimal radians;

    private Angle(BigDecimal radians) {
        radians = radians.remainder(TAU);
        this.radians = radians;
    }

    public static Angle radians(BigDecimal quantity) {
        return new Angle(quantity);
    }

    public static Angle radians(float quantity) {
        return new Angle(new BigDecimal(quantity));
    }

    public BigDecimal asRadians() {
        return radians;
    }

    public double cos() {
        return Math.cos(radians.doubleValue());
    }

    public double sin() {
        return Math.sin(radians.doubleValue());
    }

    @Override
    public String toString() {
        return radians.round(new MathContext(5)) + " rad";
    }
}
