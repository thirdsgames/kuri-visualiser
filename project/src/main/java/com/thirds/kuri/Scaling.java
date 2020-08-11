package com.thirds.kuri;

public class Scaling {
    /**
     * Ensures that the input is larger than the specified min value, when scaled by the given zoom level.
     */
    public static float softplus(float input, float camZoom, float minValue) {
        float screenRadius = input * camZoom;
        screenRadius += Math.log(1d + Math.exp(screenRadius - minValue)) + minValue;
        return screenRadius / camZoom;
    }
}
