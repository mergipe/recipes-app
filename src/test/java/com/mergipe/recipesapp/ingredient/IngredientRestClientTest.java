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

    private Ingredient savedIngredient;
    private TestRestTemplateWrapper<Ingredient> templateWrapper;

    @BeforeAll
    void setUpTestEnvironment(@Autowired TestRestTemplate template) {
        this.templateWrapper = new TestRestTemplateWrapper<>(
                Ingredient.class,
                template,
                "ingredients"
        );
        this.savedIngredient = IngredientRepositoryTestHelper
                .saveExampleIngredientWithoutReferencePrices(this.repository);
    }

    @Test
    void gettingAllIngredientsShouldReturnAnIngredientListWithoutErrors() {
        ResponseEntity<PagedModel<Ingredient>> responseEntity = this.templateWrapper.getAll();

        assertThat(responseEntity.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);

        List<Ingredient> ingredientsFromResponse =
                new ArrayList<>(responseEntity.getBody().getContent());
        List<Ingredient> ingredientsFromRepository = this.repository.findAll();

        assertThat(ingredientsFromResponse).isNotNull().isNotEmpty();
        assertThat(ingredientsFromResponse.size())
                .isEqualTo(ingredientsFromRepository.size())
                .isEqualTo(1);

        Ingredient ingredientFromResponse = ingredientsFromResponse.get(0);
        Ingredient ingredientFromRepository = ingredientsFromRepository.get(0);

        assertThat(ingredientFromResponse.getName())
                .isEqualTo(ingredientFromRepository.getName());
        assertThat(ingredientFromResponse.getBrand())
                .isEqualTo(ingredientFromRepository.getBrand());
        assertThat(ingredientFromResponse.getNutritionFacts())
                .isEqualToComparingFieldByField(ingredientFromRepository.getNutritionFacts());
    }

    @Test
    void gettingAnIngredientByIdShouldReturnTheCorrectIngredientWithoutErrors() {
        ResponseEntity<EntityModel<Ingredient>> responseEntity = this.templateWrapper
                .getOne(this.savedIngredient.getId());

        assertThat(responseEntity.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);

        Ingredient ingredient = responseEntity.getBody().getContent();
        Ingredient ingredientFromRepository = this.repository
                .findById(this.savedIngredient.getId())
                .get();

        assertThat(ingredient.getName())
                .isEqualTo(this.savedIngredient.getName())
                .isEqualTo(ingredientFromRepository.getName());
        assertThat(ingredient.getNutritionFacts())
                .isEqualToComparingFieldByField(this.savedIngredient.getNutritionFacts())
                .isEqualTo(ingredientFromRepository.getNutritionFacts());
    }
}
