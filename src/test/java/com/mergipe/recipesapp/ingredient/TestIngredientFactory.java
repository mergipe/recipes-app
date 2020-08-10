package com.mergipe.recipesapp.ingredient;

final class TestIngredientFactory {

    private static final String name = "Ingrediente";
    private static final NutritionFacts nutritionFacts = new NutritionFacts();

    private TestIngredientFactory() {
    }

    static Ingredient withoutReferencePrices() {
        return new Ingredient(
                TestIngredientFactory.name,
                TestIngredientFactory.nutritionFacts
        );
    }

    static Ingredient withOneExampleReferencePrice() {
        Ingredient ingredient = new Ingredient(
                TestIngredientFactory.name,
                TestIngredientFactory.nutritionFacts
        );
        ingredient.addReferencePrice(TestReferencePriceFactory.ofIngredient(ingredient));

        return ingredient;
    }
}
