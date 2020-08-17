package com.mergipe.recipesapp.ingredient;

import com.mergipe.recipesapp.measure.ScalarQuantity;
import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Assertions;

import java.math.BigDecimal;

public class ReferencePriceAssert extends AbstractAssert<ReferencePriceAssert, ReferencePrice> {

    private ReferencePriceAssert(ReferencePrice actual) {
        super(actual, ReferencePriceAssert.class);
    }

    public static ReferencePriceAssert assertThat(ReferencePrice actual) {
        return new ReferencePriceAssert(actual);
    }

    public ReferencePriceAssert hasIngredient(Ingredient ingredient) {
        isNotNull();

        Assertions.assertThat(actual.getIngredient())
                .overridingErrorMessage(
                        "Expected reference price's ingredient to be <%s> but was <%s>",
                        ingredient,
                        actual.getIngredient()
                )
                .isEqualToComparingFieldByField(ingredient);

        return this;
    }

    public ReferencePriceAssert hasDescription(String description) {
        isNotNull();

        Assertions.assertThat(actual.getDescription())
                .overridingErrorMessage(
                        "Expected reference price's description to be <%s> but was <%s>",
                        description,
                        actual.getDescription()
                )
                .isEqualTo(description);

        return this;
    }

    public ReferencePriceAssert hasAmount(ScalarQuantity amount) {
        isNotNull();

        Assertions.assertThat(actual.getAmount())
                .overridingErrorMessage(
                        "Expected reference price's amount to be <%s> but was <%s>",
                        amount,
                        actual.getAmount()
                )
                .isEqualTo(amount);

        return this;
    }

    public ReferencePriceAssert hasPrice(BigDecimal price) {
        isNotNull();

        Assertions.assertThat(actual.getPrice())
                .overridingErrorMessage(
                        "Expected reference price's price to be <%s> but was <%s>",
                        price,
                        actual.getPrice()
                )
                .isEqualTo(price);

        return this;
    }
}
