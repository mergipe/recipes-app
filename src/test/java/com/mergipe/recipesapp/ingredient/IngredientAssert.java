package com.mergipe.recipesapp.ingredient;

import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Assertions;

public class IngredientAssert extends AbstractAssert<IngredientAssert, Ingredient> {

    public IngredientAssert(Ingredient actual) {
        super(actual, IngredientAssert.class);
    }

    public static IngredientAssert assertThat(Ingredient actual) {
        return new IngredientAssert(actual);
    }

    public IngredientAssert hasName(String name) {
        isNotNull();

        Assertions.assertThat(actual.getName())
                .overridingErrorMessage(
                        "Expected ingredient's name to be <%s> but was <%s>",
                        name,
                        actual.getName()
                )
                .isEqualTo(name);

        return this;
    }

    public IngredientAssert hasBrand(String brand) {
        isNotNull();

        Assertions.assertThat(actual.getBrand())
                .overridingErrorMessage(
                        "Expected ingredient's brand to be <%s> but was <%s>",
                        brand,
                        actual.getBrand()
                )
                .isEqualTo(brand);

        return this;
    }

    public IngredientAssert hasNutritionFacts(NutritionFacts nutritionFacts) {
        isNotNull();

        Assertions.assertThat(actual.getNutritionFacts())
                .overridingErrorMessage(
                        "Expected ingredient's nutrition facts to be <%s> but was" +
                                " <%s>",
                        nutritionFacts,
                        actual.getNutritionFacts()
                )
                .isEqualTo(nutritionFacts);

        return this;
    }

    public IngredientAssert hasNoReferencePrice() {
        isNotNull();

        Assertions.assertThat(actual.getReferencePrices())
                .overridingErrorMessage(
                        "Expected ingredient's reference prices list size to be" +
                                " <%s> but was <%s>",
                        0,
                        actual.getReferencePrices().size()
                )
                .isEmpty();

        return this;
    }

    public IngredientAssert hasReferencePrices() {
        isNotNull();

        Assertions.assertThat(actual.getReferencePrices())
                .overridingErrorMessage(
                        "Expected ingredient's reference prices list size to be" +
                                " greater than 0 but was <%s>",
                        actual.getReferencePrices().size()
                )
                .isNotEmpty();

        return this;
    }
}
