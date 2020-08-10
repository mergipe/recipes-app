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

    private Ingredient ingredientTemplate;
    private Ingredient ingredientFromRepository;

    @BeforeEach
    void saveExampleIngredient() {
        this.ingredientTemplate = ExampleIngredient.withOneExampleReferencePrice();
        this.ingredientFromRepository = this.ingredientRepository
                .saveAndFlush(this.ingredientTemplate);
    }

    @Test
    void testAddReferencePriceToIngredient() {
        assertThat(this.referencePriceRepository.count()).isEqualTo(1);

        ReferencePrice referencePriceFromIngredient = this.ingredientFromRepository
                .getReferencePrices()
                .get(0);
        ReferencePrice referencePriceFromRepository = this.referencePriceRepository
                .findById(referencePriceFromIngredient.getId())
                .get();

        assertThat(referencePriceFromIngredient.getIngredient())
                .isEqualToComparingFieldByField(referencePriceFromRepository.getIngredient())
                .isEqualToComparingFieldByField(this.ingredientFromRepository);
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
        ReferencePrice referencePrice = this.ingredientFromRepository.getReferencePrices().get(0);
        this.ingredientFromRepository.removeReferencePrice(referencePrice);
        this.ingredientFromRepository = this.ingredientRepository
                .saveAndFlush(this.ingredientFromRepository);

        assertThat(this.ingredientFromRepository.getReferencePrices().size())
                .isEqualTo(0)
                .isEqualTo(this.referencePriceRepository.count());
    }

    @Test
    void checkThatRemovingIngredientFromReferencePriceThrowsException() {
        ReferencePrice referencePrice = this.ingredientFromRepository.getReferencePrices().get(0);
        referencePrice.setIngredient(null);

        assertThatThrownBy(() -> {
            this.referencePriceRepository.saveAndFlush(referencePrice);
        }).isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    void testDeleteIngredientCascadeReferencePrices() {
        assertThat(this.ingredientRepository.count()).isEqualTo(1);
        assertThat(this.referencePriceRepository.count()).isEqualTo(1);

        this.ingredientRepository.delete(this.ingredientFromRepository);

        assertThat(this.ingredientRepository.count()).isEqualTo(0);
        assertThat(this.referencePriceRepository.count()).isEqualTo(0);
    }
}
