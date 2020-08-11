package com.thirds.kuri.position;

import com.thirds.kuri.unit.Position;
import com.thirds.kuri.unit.Time;

public class StaticPosition implements ProceduralPosition {
    private final Position position;

    public StaticPosition(Position position) {
        this.position = position;
    }

    @Override
    public Position getPositionAtTime(Time time) {
        return position;
    }
}
