package com.thirds.kuri.unit;

import java.math.BigDecimal;

public class Mass {
    public final BigDecimal kg;

    private Mass(BigDecimal kg) {
        this.kg = kg;
    }

    public static Mass kilograms(float quantity) {
        return new Mass(new BigDecimal(quantity));
    }

    public static Mass solarMasses(float quantity) {
        return new Mass(new BigDecimal("1 988 470 000 000 000 000 000 000 000 000".replace(" ", "")).multiply(new BigDecimal(quantity)));
    }

    public static Mass earthMasses(float quantity) {
        return new Mass(new BigDecimal("5 972 000 000 000 000 000 000 000".replace(" ", "")).multiply(new BigDecimal(quantity)));
    }
}
