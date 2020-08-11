package com.mergipe.recipesapp.ingredient;

import com.mergipe.recipesapp.measure.MeasurementUnit;
import com.mergipe.recipesapp.measure.ScalarQuantity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
class IngredientDataJpaTest {

    @Autowired
    private IngredientRepository repository;

    private Ingredient testIngredient;

    @BeforeEach
    void setUpTestEnvironment() {
        this.testIngredient = IngredientDataJpaTestHelper
                .saveExampleIngredientWithoutReferencePrices(this.repository);
    }

    @Test
    void checkThatCreatingIngredientWithNullNutritionFactsThrowsException() {
        assertThatThrownBy(() -> {
            this.repository.saveAndFlush(new Ingredient(
                    "name", "brand", null)
            );
        }).isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    void testCreateBasicIngredient() {
        Ingredient referenceIngredient = TestIngredientFactory.withoutReferencePrices();

        assertThat(this.repository.count()).isEqualTo(1);
        assertThat(this.testIngredient.getName())
                .isEqualTo(referenceIngredient.getName());
        assertThat(this.testIngredient.getBrand())
                .isEqualTo(referenceIngredient.getBrand());
        assertThat(this.testIngredient.getNutritionFacts())
                .isEqualToComparingFieldByField(referenceIngredient.getNutritionFacts());
    }

    @Test
    void testUpdateNutritionFactsAttributes() {
        NutritionFacts nutritionFacts = this.testIngredient.getNutritionFacts();
        nutritionFacts.setCalories(10);
        nutritionFacts.setTotalCarbohydrate(20);
        nutritionFacts.setProtein(30);
        nutritionFacts.setTotalFat(40);
        nutritionFacts.setSaturatedFat(50);
        nutritionFacts.setTransFat(60);
        nutritionFacts.setDietaryFiber(70);
        nutritionFacts.setSodium(80);
        nutritionFacts.setPrimaryServingSize(new ScalarQuantity(
                1000, MeasurementUnit.MILLILITER
        ));
        nutritionFacts.setSecondaryServingSize(new ScalarQuantity(
                10, MeasurementUnit.UNIT
        ));
        this.testIngredient = this.repository.saveAndFlush(this.testIngredient);
        nutritionFacts = this.testIngredient.getNutritionFacts();

        assertThat(nutritionFacts.getCalories()).isEqualTo(10);
        assertThat(nutritionFacts.getTotalCarbohydrate()).isEqualTo(20);
        assertThat(nutritionFacts.getProtein()).isEqualTo(30);
        assertThat(nutritionFacts.getTotalFat()).isEqualTo(40);
        assertThat(nutritionFacts.getSaturatedFat()).isEqualTo(50);
        assertThat(nutritionFacts.getTransFat()).isEqualTo(60);
        assertThat(nutritionFacts.getDietaryFiber()).isEqualTo(70);
        assertThat(nutritionFacts.getSodium()).isEqualTo(80);
        assertThat(nutritionFacts.getPrimaryServingSize()).matches(nf ->
                nf.getMagnitude() == 1000 &&
                        nf.getMeasurementUnit() == MeasurementUnit.MILLILITER
        );
        assertThat(nutritionFacts.getSecondaryServingSize()).matches(nf ->
                nf.getMagnitude() == 10 &&
                        nf.getMeasurementUnit() == MeasurementUnit.UNIT
        );
    }

    @Test
    void checkThatSettingNutritionFactsToNullThrowsException() {
        this.testIngredient.setNutritionFacts(null);

        assertThatThrownBy(() -> {
            this.repository.saveAndFlush(this.testIngredient);
        }).isInstanceOf(DataIntegrityViolationException.class);
    }
}
