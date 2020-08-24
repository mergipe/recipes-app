package com.mergipe.recipesapp.ingredient;

import com.mergipe.recipesapp.measure.MeasurementUnit;
import com.mergipe.recipesapp.measure.ScalarQuantity;

import java.math.BigDecimal;

public class TestReferencePriceFactory {

    private static final String DESCRIPTION = "description";
    private static final BigDecimal PRICE = new BigDecimal("5.0");
    private static final double AMOUNT_MAGNITUDE = 500;
    private static final MeasurementUnit AMOUNT_MEASUREMENT_UNIT = MeasurementUnit.GRAM;

    private TestReferencePriceFactory() {
    }

    public static ReferencePrice withoutIngredient() {
        return new ReferencePrice(
                null,
                DESCRIPTION,
                new ScalarQuantity(
                        AMOUNT_MAGNITUDE,
                        AMOUNT_MEASUREMENT_UNIT
                ),
                PRICE
        );
    }
}
