package com.mergipe.recipesapp.ingredient;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
class ReferencePriceDataJpaTest {

    @Autowired
    private IngredientRepository ingredientRepository;

    @Autowired
    private ReferencePriceRepository referencePriceRepository;

    private Ingredient testIngredient;

    @BeforeEach
    void setUpTestEnvironment() {
        this.testIngredient = IngredientDataJpaTestHelper
                .saveExampleIngredientWithOneReferencePrice(this.ingredientRepository);
    }

    @Test
    void testAddReferencePriceToIngredient() {
        assertThat(this.referencePriceRepository.count()).isEqualTo(1);

        ReferencePrice referencePriceFromIngredient = this.testIngredient
                .getReferencePrices()
                .get(0);
        ReferencePrice referencePriceFromRepository = this.referencePriceRepository
                .findById(referencePriceFromIngredient.getId())
                .get();

        assertThat(referencePriceFromIngredient.getIngredient())
                .isEqualToComparingFieldByField(referencePriceFromRepository.getIngredient())
                .isEqualToComparingFieldByField(this.testIngredient);
        assertThat(referencePriceFromIngredient.getBrand())
                .isEqualTo(referencePriceFromRepository.getBrand());
        assertThat(referencePriceFromIngredient.getDescription())
                .isEqualTo(referencePriceFromRepository.getDescription());
        assertThat(referencePriceFromIngredient.getAmount())
                .isEqualToComparingFieldByField(referencePriceFromRepository.getAmount());
        assertThat(referencePriceFromIngredient.getPrice())
                .isEqualByComparingTo(referencePriceFromRepository.getPrice());
    }

    @Test
    void testRemoveReferencePriceFromIngredient() {
        ReferencePrice referencePrice = this.testIngredient.getReferencePrices().get(0);
        this.testIngredient.removeReferencePrice(referencePrice);
        this.testIngredient = this.ingredientRepository
                .saveAndFlush(this.testIngredient);

        assertThat(this.testIngredient.getReferencePrices().size())
                .isEqualTo(0)
                .isEqualTo(this.referencePriceRepository.count());
    }

    @Test
    void checkThatRemovingIngredientFromReferencePriceThrowsException() {
        ReferencePrice referencePrice = this.testIngredient.getReferencePrices().get(0);
        referencePrice.setIngredient(null);

        assertThatThrownBy(() -> {
            this.referencePriceRepository.saveAndFlush(referencePrice);
        }).isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    void testDeleteIngredientCascadeReferencePrices() {
        assertThat(this.ingredientRepository.count()).isEqualTo(1);
        assertThat(this.referencePriceRepository.count()).isEqualTo(1);

        this.ingredientRepository.delete(this.testIngredient);

        assertThat(this.ingredientRepository.count()).isEqualTo(0);
        assertThat(this.referencePriceRepository.count()).isEqualTo(0);
    }
}
