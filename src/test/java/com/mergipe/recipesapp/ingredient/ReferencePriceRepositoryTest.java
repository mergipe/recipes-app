package com.mergipe.recipesapp.ingredient;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
class ReferencePriceRepositoryTest {

    @Autowired
    private IngredientRepository ingredientRepository;

    @Autowired
    private ReferencePriceRepository referencePriceRepository;

    private Ingredient savedIngredient;

    @BeforeEach
    void setUpTestEnvironment() {
        this.savedIngredient = IngredientRepositoryTestHelper
                .saveExampleIngredientWithOneReferencePrice(this.ingredientRepository);
    }

    @Test
    void addingReferencePriceToIngredientShouldCorrectlyPersistItsAttributes() {
        assertThat(this.referencePriceRepository.count()).isEqualTo(1);

        ReferencePrice referencePriceFromSavedIngredient = this.savedIngredient
                .getReferencePrices()
                .get(0);
        ReferencePrice referencePriceFromRepository = this.referencePriceRepository
                .findById(referencePriceFromSavedIngredient.getId())
                .get();

        assertThat(referencePriceFromSavedIngredient.getIngredient())
                .isEqualToComparingFieldByField(referencePriceFromRepository.getIngredient())
                .isEqualToComparingFieldByField(this.savedIngredient);
        assertThat(referencePriceFromSavedIngredient.getDescription())
                .isEqualTo(referencePriceFromRepository.getDescription());
        assertThat(referencePriceFromSavedIngredient.getAmount())
                .isEqualToComparingFieldByField(referencePriceFromRepository.getAmount());
        assertThat(referencePriceFromSavedIngredient.getPrice())
                .isEqualByComparingTo(referencePriceFromRepository.getPrice());
    }

    @Test
    void removingReferencePriceFromIngredientShouldAlsoDeleteTheReferencePrice() {
        ReferencePrice referencePrice = this.savedIngredient.getReferencePrices().get(0);
        this.savedIngredient.removeReferencePrice(referencePrice);
        this.savedIngredient = this.ingredientRepository.saveAndFlush(this.savedIngredient);

        assertThat(this.savedIngredient.getReferencePrices().size())
                .isEqualTo(0)
                .isEqualTo(this.referencePriceRepository.count());
    }

    @Test
    void removingTheIngredientAttributeFromReferencePriceShouldThrowDataIntegrityViolationException() {
        ReferencePrice referencePrice = this.savedIngredient.getReferencePrices().get(0);
        referencePrice.setIngredient(null);

        assertThatThrownBy(() -> {
            this.referencePriceRepository.saveAndFlush(referencePrice);
        }).isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    void deletingIngredientShouldAlsoDeleteAllItsReferencePrices() {
        assertThat(this.ingredientRepository.count()).isEqualTo(1);
        assertThat(this.referencePriceRepository.count()).isEqualTo(1);

        this.ingredientRepository.delete(this.savedIngredient);

        assertThat(this.ingredientRepository.count()).isEqualTo(0);
        assertThat(this.referencePriceRepository.count()).isEqualTo(0);
    }
}
