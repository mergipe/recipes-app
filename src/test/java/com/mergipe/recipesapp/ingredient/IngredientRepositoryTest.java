package com.mergipe.recipesapp.ingredient;

import com.mergipe.recipesapp.measure.MeasurementUnit;
import com.mergipe.recipesapp.measure.ScalarQuantity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import static com.mergipe.recipesapp.ingredient.IngredientAssert.assertThat;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
class IngredientRepositoryTest {

    @Autowired
    private IngredientRepository repository;

    private Ingredient savedIngredient;

    @BeforeEach
    void setUpTestEnvironment() {
        this.savedIngredient = IngredientRepositoryTestHelper
                .saveExampleIngredientWithoutReferencePrices(this.repository);
    }

    @Test
    void creatingIngredientWithNullNutritionFactsShouldThrowDataIntegrityViolationException() {
        assertThatThrownBy(() -> {
            this.repository.saveAndFlush(new Ingredient(
                    "name",
                    "brand",
                    null)
            );
        }).isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    void creatingIngredientWithoutReferencePricesShouldCorrectlyPersistItsAttributes() {
        Ingredient referenceIngredient = TestIngredientFactory.withoutReferencePrices();

        assertThat(this.repository.count()).isEqualTo(1);
        assertThat(this.savedIngredient)
                .hasName(referenceIngredient.getName())
                .hasBrand(referenceIngredient.getBrand())
                .hasNutritionFacts(referenceIngredient.getNutritionFacts());
    }

    @Test
    void updatingIngredientNutritionFactsShouldCorrectlyPersistItsAttributes() {
        NutritionFacts savedNutritionFacts = this.savedIngredient.getNutritionFacts();
        savedNutritionFacts.setCalories(10);
        savedNutritionFacts.setTotalCarbohydrate(20);
        savedNutritionFacts.setProtein(30);
        savedNutritionFacts.setTotalFat(40);
        savedNutritionFacts.setSaturatedFat(50);
        savedNutritionFacts.setTransFat(60);
        savedNutritionFacts.setDietaryFiber(70);
        savedNutritionFacts.setSodium(80);
        savedNutritionFacts.setPrimaryServingSize(new ScalarQuantity(
                1000, MeasurementUnit.MILLILITER
        ));
        savedNutritionFacts.setSecondaryServingSize(new ScalarQuantity(
                10, MeasurementUnit.UNIT
        ));

        Ingredient updatedIngredient = this.repository.saveAndFlush(this.savedIngredient);
        NutritionFacts updatedNutritionFacts = updatedIngredient.getNutritionFacts();

        assertThat(updatedNutritionFacts).isEqualTo(savedNutritionFacts);
    }

    @Test
    void settingNutritionFactsToNullShouldThrowDataIntegrityViolationException() {
        this.savedIngredient.setNutritionFacts(null);

        assertThatThrownBy(() -> {
            this.repository.saveAndFlush(this.savedIngredient);
        }).isInstanceOf(DataIntegrityViolationException.class);
    }
}
