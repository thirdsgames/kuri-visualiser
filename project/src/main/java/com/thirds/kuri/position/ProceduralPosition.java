package com.thirds.kuri.position;

import com.thirds.kuri.unit.Position;
import com.thirds.kuri.unit.Time;

public interface ProceduralPosition {
    Position getPositionAtTime(Time time);
}
