package com.mergipe.recipesapp.ingredient;

import com.mergipe.recipesapp.measure.MeasurementUnit;
import com.mergipe.recipesapp.measure.ScalarQuantity;

import java.math.BigDecimal;

final class TestReferencePriceFactory {

    private static final String description = "reference price description";
    private static final BigDecimal price = new BigDecimal("5.0");

    private TestReferencePriceFactory() {
    }

    static ReferencePrice ofIngredient(Ingredient ingredient) {
        return new ReferencePrice(
                ingredient,
                TestReferencePriceFactory.description,
                new ScalarQuantity(500, MeasurementUnit.GRAM),
                TestReferencePriceFactory.price
        );
    }
}
