package com.thirds.kuri;

import com.thirds.kuri.unit.Length;
import com.thirds.kuri.unit.Mass;

/**
 * Represents a body in space. This may be a planet, asteroid, star, spaceship etc.
 */
public class Body {
    private final Mass mass;
    private final Length radius;

    public Body(Mass mass, Length radius) {
        this.mass = mass;
        this.radius = radius;
    }

    public Mass getMass() {
        return mass;
    }

    public Length getRadius() {
        return radius;
    }
}
