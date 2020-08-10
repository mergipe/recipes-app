package com.mergipe.recipesapp.ingredient;

final class IngredientDataJpaTestHelper {

    private IngredientDataJpaTestHelper() {
    }

    static Ingredient saveExampleIngredientWithoutReferencePrices(IngredientRepository repository) {
        return repository.saveAndFlush(TestIngredientFactory.withoutReferencePrices());
    }

    static Ingredient saveExampleIngredientWithOneReferencePrice(IngredientRepository repository) {
        return repository.saveAndFlush(TestIngredientFactory.withOneExampleReferencePrice());
    }
}
