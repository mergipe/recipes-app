package com.mergipe.recipesapp.ingredient;

import com.mergipe.recipesapp.measure.MeasurementUnit;
import com.mergipe.recipesapp.measure.ScalarQuantity;

public class TestIngredientFactory {

    private static final String NAME = "name";
    private static final String BRAND = "brand";
    private static final double CALORIES = 1;
    private static final double TOTAL_CARBOHYDRATE = 2;
    private static final double PROTEIN = 3;
    private static final double TOTAL_FAT = 4;
    private static final double SATURATED_FAT = 5;
    private static final double TRANS_FAT = 6;
    private static final double DIETARY_FIBER = 7;
    private static final double SODIUM = 8;
    private static final double PRIMARY_SERVING_SIZE_MAGNITUDE = 100;
    private static final MeasurementUnit PRIMARY_SERVING_SIZE_MEASUREMENT_UNIT =
            MeasurementUnit.GRAM;
    private static final double SECONDARY_SERVING_SIZE_MAGNITUDE = 1;
    private static final MeasurementUnit SECONDARY_SERVING_SIZE_MEASUREMENT_UNIT =
            MeasurementUnit.CUP;

    private TestIngredientFactory() {
    }

    private static NutritionFacts createNutritionFacts() {
        return new NutritionFacts(
                new ScalarQuantity(
                        PRIMARY_SERVING_SIZE_MAGNITUDE,
                        PRIMARY_SERVING_SIZE_MEASUREMENT_UNIT
                ),
                new ScalarQuantity(
                        SECONDARY_SERVING_SIZE_MAGNITUDE,
                        SECONDARY_SERVING_SIZE_MEASUREMENT_UNIT
                ),
                CALORIES,
                TOTAL_CARBOHYDRATE,
                PROTEIN,
                TOTAL_FAT,
                SATURATED_FAT,
                TRANS_FAT,
                DIETARY_FIBER,
                SODIUM
        );
    }

    public static Ingredient withoutReferencePrices() {
        return new Ingredient(
                NAME,
                BRAND,
                createNutritionFacts()
        );
    }

    public static Ingredient withOneReferencePrice() {
        Ingredient ingredient = withoutReferencePrices();
        ingredient.addReferencePrice(TestReferencePriceFactory.withoutIngredient());
        return ingredient;
    }
}
