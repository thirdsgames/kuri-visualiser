package com.thirds.kuri.unit;

import java.math.BigDecimal;
import java.math.MathContext;

public class AngularVelocity {
    private final BigDecimal radiansPerSecond;

    private AngularVelocity(BigDecimal radiansPerSecond) {
        this.radiansPerSecond = radiansPerSecond;
    }

    public static AngularVelocity radiansPerSecond(BigDecimal quantity) {
        return new AngularVelocity(quantity);
    }

    public BigDecimal asRadiansPerSecond() {
        return radiansPerSecond;
    }

    public Angle multiply(Time time) {
        return Angle.radians(radiansPerSecond.multiply(time.seconds, MathContext.DECIMAL128));
    }
}
