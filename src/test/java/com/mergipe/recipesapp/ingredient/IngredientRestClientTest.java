package com.mergipe.recipesapp.ingredient;

import com.mergipe.recipesapp.RestClientTestConfiguration;
import com.mergipe.recipesapp.TestRestTemplateWrapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@ContextConfiguration(classes = RestClientTestConfiguration.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class IngredientRestClientTest {

    @Autowired
    private IngredientRepository repository;

    private Ingredient testIngredient;
    private TestRestTemplateWrapper<Ingredient> templateWrapper;

    @BeforeAll
    void setUpTestEnvironment(@Autowired TestRestTemplate template) {
        this.templateWrapper = new TestRestTemplateWrapper<>(
                Ingredient.class,
                template,
                "ingredients"
        );
        this.testIngredient = IngredientRepositoryTestHelper
                .saveExampleIngredientWithoutReferencePrices(this.repository);
    }

    @Test
    void testGetAllIngredients() {
        ResponseEntity<PagedModel<Ingredient>> responseEntity = this.templateWrapper.getAll();

        assertThat(responseEntity.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);

        List<Ingredient> ingredients = new ArrayList<>(responseEntity.getBody().getContent());
        List<Ingredient> ingredientsFromRepository = this.repository.findAll();

        assertThat(ingredients).isNotNull().isNotEmpty();
        assertThat(ingredients.size())
                .isEqualTo(ingredientsFromRepository.size())
                .isEqualTo(1);

        Ingredient ingredient = ingredients.get(0);
        Ingredient ingredientFromRepository = ingredientsFromRepository.get(0);

        assertThat(ingredient.getName()).isEqualTo(ingredientFromRepository.getName());
        assertThat(ingredient.getBrand()).isEqualTo(ingredientFromRepository.getBrand());
        assertThat(ingredient.getNutritionFacts())
                .isEqualToComparingFieldByField(ingredientFromRepository.getNutritionFacts());
    }

    @Test
    void testGetOneIngredient() {
        ResponseEntity<EntityModel<Ingredient>> responseEntity = this.templateWrapper
                .getOne(this.testIngredient.getId());

        assertThat(responseEntity.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);

        Ingredient ingredient = responseEntity.getBody().getContent();
        Ingredient ingredientFromRepository = this.repository
                .findById(this.testIngredient.getId())
                .get();

        assertThat(ingredient.getName())
                .isEqualTo(this.testIngredient.getName())
                .isEqualTo(ingredientFromRepository.getName());
        assertThat(ingredient.getNutritionFacts())
                .isEqualToComparingFieldByField(this.testIngredient.getNutritionFacts())
                .isEqualTo(ingredientFromRepository.getNutritionFacts());
    }
}
