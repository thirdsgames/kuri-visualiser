package com.thirds.kuri;

public class Scaling {
    /**
     * Ensures that the input is larger than the specified min value, when scaled by the given zoom level.
     * Don't use with extreme input values.
     */
    public static float softplus(float input, float camZoom, float minValue) {
        float screenValue = input * camZoom;
        screenValue += Math.log(1d + Math.exp(screenValue - minValue)) + minValue;
        return screenValue / camZoom;
    }

    public static float softerplus(float input, float camZoom, float minValue) {
        float screenValue = input * camZoom;
        if (screenValue < minValue) {
            return minValue / camZoom;
        }
        return screenValue / camZoom;
    }
}
