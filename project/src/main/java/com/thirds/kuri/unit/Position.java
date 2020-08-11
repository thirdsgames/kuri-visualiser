package com.thirds.kuri.unit;

import com.badlogic.gdx.math.Vector2;

public class Position {
    private final Length x, y;

    private Position(Length x, Length y) {
        this.x = x;
        this.y = y;
    }

    public static Position cartesian(Length x, Length y) {
        return new Position(x, y);
    }

    /**
     * Converts x and y to astronomical units.
     */
    public Vector2 asScreenPosition() {
        return new Vector2(x.asAstronomicalUnits().floatValue(), y.asAstronomicalUnits().floatValue());
    }
}
