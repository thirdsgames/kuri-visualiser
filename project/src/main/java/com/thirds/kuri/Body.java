package com.thirds.kuri;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.thirds.kuri.position.ProceduralPosition;
import com.thirds.kuri.unit.Length;
import com.thirds.kuri.unit.Mass;
import com.thirds.kuri.unit.Position;
import com.thirds.kuri.unit.Time;

/**
 * Represents a body in space. This may be a planet, asteroid, star, spaceship etc.
 */
public class Body {
    private final Mass mass;
    private final Length radius;
    private final ProceduralPosition position;

    public Body(Mass mass, Length radius, ProceduralPosition position) {
        this.mass = mass;
        this.radius = radius;
        this.position = position;
    }

    public Mass getMass() {
        return mass;
    }

    public Length getRadius() {
        return radius;
    }

    public ProceduralPosition getPosition() {
        return position;
    }

    public void render(ShapeRenderer sr, float viewZoom, Time time) {
        Vector2 screenPos = position.getPositionAtTime(time).asScreenPosition();
        float radius = getRadius().asAstronomicalUnits().floatValue();

        // Make sure that the apparent radius is no smaller than ~5 px.
        final float MIN_SIZE = 5f;
        float screenRadius = radius * viewZoom;
        screenRadius += MIN_SIZE * Math.exp(-screenRadius);
        radius = screenRadius / viewZoom;

        sr.setColor(Color.YELLOW);
        sr.circle(screenPos.x, screenPos.y, radius);
    }
}
