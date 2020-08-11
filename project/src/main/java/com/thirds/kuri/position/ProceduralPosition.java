package com.thirds.kuri.position;

import com.thirds.kuri.unit.Position;
import com.thirds.kuri.unit.Time;

public interface ProceduralPosition {
    /**
     * Camera zoom may be used to alter the position of bodies so that they don't overlap when zoomed out.
     */
    Position getPositionAtTime(float camZoom, Time time);
}
