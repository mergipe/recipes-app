package com.mergipe.recipesapp.ingredient;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;

import static com.mergipe.recipesapp.ingredient.IngredientAssert.assertThat;
import static com.mergipe.recipesapp.ingredient.ReferencePriceAssert.assertThat;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
class ReferencePriceRepositoryTest {

    @Autowired
    private IngredientRepository ingredientRepository;

    @Autowired
    private ReferencePriceRepository referencePriceRepository;

    private Ingredient testIngredient;
    private ReferencePrice testReferencePrice;

    @BeforeEach
    void createTestObjects() {
        this.testIngredient = TestIngredientFactory.withoutReferencePrices();
        this.testReferencePrice = TestReferencePriceFactory.withoutIngredient();
    }

    @Nested
    @DataJpaTest
    class WhenDatabaseIsEmpty {

        @Nested
        @DataJpaTest
        class Create {

            @Test
            void creatingIngredientWithOneReferencePriceShouldCorrectlyPersistItsAttributes() {
                testIngredient.addReferencePrice(testReferencePrice);
                Ingredient createdIngredient = ingredientRepository.saveAndFlush(testIngredient);
                List<ReferencePrice> referencePrices = createdIngredient.getReferencePrices();

                assertThat(referencePriceRepository.count()).isEqualTo(1);
                assertThat(createdIngredient)
                        .hasName(testIngredient.getName())
                        .hasBrand(testIngredient.getBrand())
                        .hasNutritionFacts(testIngredient.getNutritionFacts())
                        .hasReferencePrices();
                assertThat(referencePrices.size())
                        .isEqualTo(1);
                assertThat(referencePrices.get(0))
                        .hasIngredient(testReferencePrice.getIngredient())
                        .hasDescription(testReferencePrice.getDescription())
                        .hasAmount(testReferencePrice.getAmount())
                        .hasPrice(testReferencePrice.getPrice());
            }
        }
    }

    @Nested
    @DataJpaTest
    class WhenDatabaseHasOneIngredient {

        private Ingredient savedIngredient;

        private void addReferencePriceToSavedIngredient() {
            this.savedIngredient.addReferencePrice(testReferencePrice);
            this.savedIngredient = ingredientRepository.saveAndFlush(savedIngredient);
        }

        @BeforeEach
        void persistTestIngredientWithoutReferencePrice() {
            this.savedIngredient = ingredientRepository.saveAndFlush(testIngredient);
        }

        @Nested
        @DataJpaTest
        class WithoutReferencePrice {

            @Nested
            @DataJpaTest
            class Create {

                @Test
                void addingReferencePriceToIngredientShouldCorrectlyPersistItsAttributes() {
                    addReferencePriceToSavedIngredient();

                    assertThat(referencePriceRepository.count()).isEqualTo(1);

                    ReferencePrice referencePriceFromSavedIngredient = savedIngredient
                            .getReferencePrices()
                            .get(0);
                    ReferencePrice referencePriceFromRepository = referencePriceRepository
                            .findById(referencePriceFromSavedIngredient.getId())
                            .get();

                    assertThat(referencePriceFromRepository)
                            .hasIngredient(referencePriceFromSavedIngredient.getIngredient())
                            .hasDescription(referencePriceFromSavedIngredient.getDescription())
                            .hasAmount(referencePriceFromSavedIngredient.getAmount())
                            .hasPrice(referencePriceFromSavedIngredient.getPrice());
                    assertThat(referencePriceFromSavedIngredient)
                            .hasIngredient(testReferencePrice.getIngredient())
                            .hasDescription(testReferencePrice.getDescription())
                            .hasAmount(testReferencePrice.getAmount())
                            .hasPrice(testReferencePrice.getPrice());
                }
            }
        }

        @Nested
        @DataJpaTest
        class WithOneReferencePrice {

            @BeforeEach
            void updateSavedIngredient() {
                addReferencePriceToSavedIngredient();
            }

            @Nested
            @DataJpaTest
            class Delete {

                @Test
                void removingReferencePriceFromIngredientShouldDeleteTheReferencePriceFromDatabase() {
                    ReferencePrice referencePrice = savedIngredient.getReferencePrices().get(0);
                    savedIngredient.removeReferencePrice(referencePrice);
                    savedIngredient = ingredientRepository.saveAndFlush(savedIngredient);

                    IngredientAssert.assertThat(savedIngredient).hasNoReferencePrice();
                    assertThat(referencePriceRepository.count()).isEqualTo(0);
                }

                @Test
                void throwsDataIntegrityViolationExceptionWhenRemovingTheIngredientAttributeFromReferencePrice() {
                    ReferencePrice referencePrice = savedIngredient.getReferencePrices().get(0);
                    referencePrice.setIngredient(null);

                    assertThatThrownBy(() -> referencePriceRepository.saveAndFlush(referencePrice))
                            .isInstanceOf(DataIntegrityViolationException.class);
                }

                @Test
                void deletingIngredientShouldAlsoDeleteAllItsReferencePrices() {
                    assertThat(ingredientRepository.count()).isEqualTo(1);
                    assertThat(referencePriceRepository.count()).isEqualTo(1);

                    ingredientRepository.delete(savedIngredient);

                    assertThat(ingredientRepository.count()).isEqualTo(0);
                    assertThat(referencePriceRepository.count()).isEqualTo(0);
                }
            }
        }
    }
}
