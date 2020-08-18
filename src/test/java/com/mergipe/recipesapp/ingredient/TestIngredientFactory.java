package com.mergipe.recipesapp.ingredient;

import com.mergipe.recipesapp.measure.MeasurementUnit;
import com.mergipe.recipesapp.measure.ScalarQuantity;

final class TestIngredientFactory {

    private static final String name = "ingredient name";
    private static final String brand = "ingredient brand";
    private static final int calories = 1;
    private static final int totalCarbohydrate = 2;
    private static final int protein = 3;
    private static final int totalFat = 4;
    private static final int saturatedFat = 5;
    private static final int transFat = 6;
    private static final int dietaryFiber = 7;
    private static final int sodium = 8;

    private TestIngredientFactory() {
    }

    private static NutritionFacts buildNutritionFacts() {
        return new NutritionFacts(
                new ScalarQuantity(100, MeasurementUnit.GRAM),
                new ScalarQuantity(1, MeasurementUnit.CUP),
                TestIngredientFactory.calories,
                TestIngredientFactory.totalCarbohydrate,
                TestIngredientFactory.protein,
                TestIngredientFactory.totalFat,
                TestIngredientFactory.saturatedFat,
                TestIngredientFactory.transFat,
                TestIngredientFactory.dietaryFiber,
                TestIngredientFactory.sodium
        );
    }

    static Ingredient withoutReferencePrices() {
        return new Ingredient(
                TestIngredientFactory.name,
                TestIngredientFactory.brand,
                TestIngredientFactory.buildNutritionFacts()
        );
    }

    static Ingredient withOneExampleReferencePrice() {
        Ingredient ingredient = TestIngredientFactory.withoutReferencePrices();
        ingredient.addReferencePrice(TestReferencePriceFactory.ofIngredient(ingredient));

        return ingredient;
    }
}
