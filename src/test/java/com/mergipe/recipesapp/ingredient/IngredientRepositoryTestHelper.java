package com.mergipe.recipesapp.ingredient;

final class IngredientRepositoryTestHelper {

    private IngredientRepositoryTestHelper() {
    }

    static Ingredient saveExampleIngredientWithoutReferencePrices(IngredientRepository repository) {
        return repository.saveAndFlush(TestIngredientFactory.withoutReferencePrices());
    }

    static Ingredient saveExampleIngredientWithOneReferencePrice(IngredientRepository repository) {
        return repository.saveAndFlush(TestIngredientFactory.withOneExampleReferencePrice());
    }
}
