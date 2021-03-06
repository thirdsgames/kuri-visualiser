package com.thirds.kuri.position;

import com.thirds.kuri.Body;
import com.thirds.kuri.Scaling;
import com.thirds.kuri.unit.*;

import java.math.BigDecimal;
import java.math.MathContext;

/**
 * Assumes a circular orbit.
 */
public class OrbitalPosition implements ProceduralPosition {
    private static final BigDecimal G = new BigDecimal("0.0000000000667408");

    /**
     * The body to orbit.
     */
    private final Body primary;
    /**
     * The distance between the primary and the secondary.
     */
    private final Length radius;
    /**
     * The angular offset of the orbit.
     */
    private final Angle offset;
    /**
     * The angular velocity of the secondary w.r.t. the primary.
     */
    private final AngularVelocity angularVelocity;
    /**
     * The time taken to complete one orbit.
     */
    private final Time timePeriod;

    public OrbitalPosition(Body primary, Length radius, Angle offset) {
        this.primary = primary;
        this.radius = radius;
        this.offset = offset;

        // omega = sqrt( G * m_1 / r^3 )
        angularVelocity = AngularVelocity.radiansPerSecond(
                G
                        .multiply(primary.getMass().kg)
                        .divide(radius.asMetres().pow(3), MathContext.DECIMAL128)
                        .sqrt(MathContext.DECIMAL128)
        );

        timePeriod = Time.seconds(Angle.TAU.divide(angularVelocity.asRadiansPerSecond(), MathContext.DECIMAL128));
    }

    @Override
    public Position getPositionAtTime(float camZoom, Time time) {
        Angle angle = angularVelocity.multiply(time);
        float apparentRadius = Scaling.softerplus(radius.asAstronomicalUnits().floatValue(), camZoom, 8);
        return Position.polar(
                Length.astronomicalUnits(apparentRadius),
                angle.add(offset)).add(primary.getPosition().getPositionAtTime(camZoom, time));
    }

    public Time getTimePeriod() {
        return timePeriod;
    }
}
