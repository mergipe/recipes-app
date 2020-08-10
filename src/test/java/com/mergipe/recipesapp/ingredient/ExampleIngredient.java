package com.mergipe.recipesapp.ingredient;

class ExampleIngredient {

    private static final String name = "Nome";
    private static final NutritionFacts nutritionFacts = new NutritionFacts();

    static Ingredient withoutReferencePrices() {
        return new Ingredient(
                ExampleIngredient.name,
                ExampleIngredient.nutritionFacts
        );
    }

    static Ingredient withOneExampleReferencePrice() {
        Ingredient ingredient = new Ingredient(
                ExampleIngredient.name,
                ExampleIngredient.nutritionFacts
        );
        ingredient.addReferencePrice(ExampleReferencePrice.ofIngredient(ingredient));

        return ingredient;
    }
}
