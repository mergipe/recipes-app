package com.mergipe.recipesapp.ingredient;

final class IngredientDataJpaTestSetup {

    private IngredientDataJpaTestSetup() {
    }

    static Ingredient saveExampleIngredientWithoutReferencePrices(IngredientRepository repository) {
        return repository.saveAndFlush(TestIngredientFactory.withoutReferencePrices());
    }

    static Ingredient saveExampleIngredientWithOneReferencePrice(IngredientRepository repository) {
        return repository.saveAndFlush(TestIngredientFactory.withOneExampleReferencePrice());
    }
}
