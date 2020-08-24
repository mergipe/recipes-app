package com.mergipe.recipesapp.ingredient;

import com.mergipe.recipesapp.measure.MeasurementUnit;
import com.mergipe.recipesapp.measure.ScalarQuantity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
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

    private Ingredient testIngredient;

    @BeforeEach
    void createTestIngredient() {
        this.testIngredient = TestIngredientFactory.withoutReferencePrices();
    }

    @Nested
    @DataJpaTest
    class WhenDatabaseIsEmpty {

        @Nested
        @DataJpaTest
        class Create {

            @Test
            void throwsDataIntegrityViolationExceptionWhenNutritionFactsIsNull() {
                testIngredient.setNutritionFacts(null);

                assertThatThrownBy(() -> repository.saveAndFlush(testIngredient))
                        .isInstanceOf(DataIntegrityViolationException.class);
            }

            @Test
            void shouldCorrectlyPersistItsAttributes() {
                Ingredient createdIngredient = repository.saveAndFlush(testIngredient);

                assertThat(repository.count()).isEqualTo(1);
                assertThat(createdIngredient)
                        .hasName(testIngredient.getName())
                        .hasBrand(testIngredient.getBrand())
                        .hasNutritionFacts(testIngredient.getNutritionFacts())
                        .hasNoReferencePrice();
            }
        }
    }

    @Nested
    @DataJpaTest
    class WhenDatabaseHasOneIngredient {

        @Nested
        @DataJpaTest
        class Update {

            private Ingredient savedIngredient;

            @BeforeEach
            void persistTestIngredient() {
                this.savedIngredient = repository.saveAndFlush(testIngredient);
            }

            @Test
            void shouldCorrectlyUpdateNutritionFactsAttributes() {
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

                Ingredient updatedIngredient = repository.saveAndFlush(this.savedIngredient);
                NutritionFacts updatedNutritionFacts = updatedIngredient.getNutritionFacts();

                assertThat(updatedNutritionFacts).isEqualTo(savedNutritionFacts);
            }

            @Test
            void throwsDataIntegrityViolationExceptionWhenNutritionFactsIsNull() {
                this.savedIngredient.setNutritionFacts(null);

                assertThatThrownBy(() -> repository.saveAndFlush(this.savedIngredient))
                        .isInstanceOf(DataIntegrityViolationException.class);
            }
        }
    }
}
