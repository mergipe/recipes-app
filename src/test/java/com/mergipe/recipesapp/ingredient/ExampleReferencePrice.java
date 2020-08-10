package com.mergipe.recipesapp.ingredient;

import com.mergipe.recipesapp.measure.MeasurementUnit;
import com.mergipe.recipesapp.measure.ScalarQuantity;

import java.math.BigDecimal;

class ExampleReferencePrice {

    private static final String brand = "Marca";
    private static final String description = "Descrição";
    private static final ScalarQuantity amount = new ScalarQuantity(
            500, MeasurementUnit.GRAM
    );
    private static final BigDecimal price = new BigDecimal("5.0");

    static ReferencePrice ofIngredient(Ingredient ingredient) {
        return new ReferencePrice(
                ingredient,
                ExampleReferencePrice.brand,
                ExampleReferencePrice.description,
                ExampleReferencePrice.amount,
                ExampleReferencePrice.price
        );
    }
}
