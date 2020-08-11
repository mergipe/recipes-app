package com.mergipe.recipesapp.ingredient;

import com.mergipe.recipesapp.measure.MeasurementUnit;
import com.mergipe.recipesapp.measure.ScalarQuantity;

final class TestIngredientFactory {

    private static final String name = "ingredient name";
    private static final String brand = "ingredient brand";
    private static final NutritionFacts nutritionFacts = new NutritionFacts(
            new ScalarQuantity(100, MeasurementUnit.GRAM),
            new ScalarQuantity(1, MeasurementUnit.CUP),
            1,
            2,
            3,
            4,
            5,
            6,
            7,
            8
    );

    private TestIngredientFactory() {
    }

    static Ingredient withoutReferencePrices() {
        return new Ingredient(
                TestIngredientFactory.name,
                TestIngredientFactory.brand,
                TestIngredientFactory.nutritionFacts
        );
    }

    static Ingredient withOneExampleReferencePrice() {
        Ingredient ingredient = TestIngredientFactory.withoutReferencePrices();
        ingredient.addReferencePrice(TestReferencePriceFactory.ofIngredient(ingredient));

        return ingredient;
    }
}
