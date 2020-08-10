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

    private Ingredient ingredientTemplate;
    private Ingredient ingredientFromRepository;

    @BeforeEach
    void saveExampleIngredient() {
        this.ingredientTemplate = ExampleIngredient.withoutReferencePrices();
        this.ingredientFromRepository = this.repository.saveAndFlush(this.ingredientTemplate);
    }

    @Test
    void checkThatCreatingIngredientWithNullNutritionFactsThrowsException() {
        assertThatThrownBy(() -> {
            this.repository.saveAndFlush(new Ingredient("nome", null));
        }).isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    void testCreateBasicIngredient() {
        assertThat(this.repository.count()).isEqualTo(1);
        assertThat(this.ingredientFromRepository.getName())
                .isEqualTo(this.ingredientTemplate.getName());
        assertThat(this.ingredientFromRepository.getNutritionFacts())
                .isEqualToComparingFieldByField(this.ingredientTemplate.getNutritionFacts());
    }

    @Test
    void testUpdateNutritionFactsAttributes() {
        NutritionFacts nutritionFacts = this.ingredientFromRepository.getNutritionFacts();
        nutritionFacts.setCalories(1);
        nutritionFacts.setTotalCarbohydrate(2);
        nutritionFacts.setProtein(3);
        nutritionFacts.setTotalFat(4);
        nutritionFacts.setSaturatedFat(5);
        nutritionFacts.setTransFat(6);
        nutritionFacts.setDietaryFiber(7);
        nutritionFacts.setSodium(8);
        nutritionFacts.setPrimaryServingSize(new ScalarQuantity(
                500, MeasurementUnit.GRAM
        ));
        nutritionFacts.setSecondaryServingSize(new ScalarQuantity(
                2, MeasurementUnit.CUP
        ));
        this.ingredientFromRepository = this.repository.saveAndFlush(this.ingredientFromRepository);
        nutritionFacts = this.ingredientFromRepository.getNutritionFacts();

        assertThat(nutritionFacts.getCalories()).isEqualTo(1);
        assertThat(nutritionFacts.getTotalCarbohydrate()).isEqualTo(2);
        assertThat(nutritionFacts.getProtein()).isEqualTo(3);
        assertThat(nutritionFacts.getTotalFat()).isEqualTo(4);
        assertThat(nutritionFacts.getSaturatedFat()).isEqualTo(5);
        assertThat(nutritionFacts.getTransFat()).isEqualTo(6);
        assertThat(nutritionFacts.getDietaryFiber()).isEqualTo(7);
        assertThat(nutritionFacts.getSodium()).isEqualTo(8);
        assertThat(nutritionFacts.getPrimaryServingSize()).matches(nf ->
                nf.getMagnitude() == 500 &&
                nf.getMeasurementUnit() == MeasurementUnit.GRAM
        );
        assertThat(nutritionFacts.getSecondaryServingSize()).matches(nf ->
                nf.getMagnitude() == 2 &&
                nf.getMeasurementUnit() == MeasurementUnit.CUP
        );
    }

    @Test
    void checkThatSettingNutritionFactsToNullThrowsException() {
        this.ingredientFromRepository.setNutritionFacts(null);

        assertThatThrownBy(() -> {
            this.repository.saveAndFlush(this.ingredientFromRepository);
        }).isInstanceOf(DataIntegrityViolationException.class);
    }
}
