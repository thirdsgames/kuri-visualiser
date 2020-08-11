package com.thirds.kuri.unit;

import java.math.BigDecimal;

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
}
