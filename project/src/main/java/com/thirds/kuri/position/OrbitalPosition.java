package com.thirds.kuri.position;

import com.thirds.kuri.Body;
import com.thirds.kuri.unit.AngularVelocity;
import com.thirds.kuri.unit.Length;
import com.thirds.kuri.unit.Position;
import com.thirds.kuri.unit.Time;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 * Assumes a circular orbit.
 */
public class OrbitalPosition implements ProceduralPosition {
    private static final BigDecimal G = new BigDecimal("0.0000000000667408");
    private static final BigDecimal TAU = new BigDecimal("6.28318531");

    /**
     * The body to orbit.
     */
    private final Body primary;
    /**
     * The distance between the primary and the secondary.
     */
    private final Length radius;
    /**
     * The angular velocity of the secondary w.r.t. the primary.
     */
    private final AngularVelocity angularVelocity;
    /**
     * The time taken to complete one orbit.
     */
    private final Time timePeriod;

    public OrbitalPosition(Body primary, Length radius) {
        this.primary = primary;
        this.radius = radius;

        // omega = sqrt( G m1 / r^3 )
        angularVelocity = AngularVelocity.radiansPerSecond(
                G
                        .multiply(primary.getMass().kg)
                        .divide(radius.asMetres().pow(3), MathContext.DECIMAL128)
                        .sqrt(MathContext.DECIMAL128)
        );

        timePeriod = Time.seconds(TAU.divide(angularVelocity.asRadiansPerSecond(), MathContext.DECIMAL128));
    }

    @Override
    public Position getPositionAtTime(Time time) {
        return null;
    }
}
