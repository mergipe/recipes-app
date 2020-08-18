package com.mergipe.recipesapp.ingredient;

import com.mergipe.recipesapp.RestClientTestConfiguration;
import com.mergipe.recipesapp.TestRestTemplateWrapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static com.mergipe.recipesapp.ingredient.IngredientAssert.assertThat;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@ContextConfiguration(classes = RestClientTestConfiguration.class)
class IngredientRestClientTest {

    @Autowired
    private IngredientRepository repository;

    private Ingredient savedIngredient;
    private TestRestTemplateWrapper<Ingredient> templateWrapper;

    IngredientRestClientTest(@Autowired TestRestTemplate template) {
        this.templateWrapper = new TestRestTemplateWrapper<>(
                Ingredient.class,
                template,
                "ingredients"
        );
    }

    @BeforeEach
    void setUpTestEnvironment() {
        this.savedIngredient = IngredientRepositoryTestHelper
                .saveExampleIngredientWithoutReferencePrices(this.repository);
    }

    @AfterEach
    void clearTestEnvironment() {
        this.repository.deleteAll();
    }

    @Test
    void gettingAllIngredientsShouldReturnAnIngredientListWithoutErrors() {
        ResponseEntity<PagedModel<Ingredient>> responseEntity = this.templateWrapper.getAll();

        assertThat(responseEntity.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);

        List<Ingredient> ingredientsFromResponse =
                new ArrayList<>(responseEntity.getBody().getContent());

        assertThat(ingredientsFromResponse)
                .isNotNull()
                .isNotEmpty();
        assertThat(ingredientsFromResponse.size())
                .isEqualTo(1);

        Ingredient ingredientFromResponse = ingredientsFromResponse.get(0);

        assertThat(ingredientFromResponse)
                .hasName(this.savedIngredient.getName())
                .hasBrand(this.savedIngredient.getBrand())
                .hasNutritionFacts(this.savedIngredient.getNutritionFacts());
    }

    @Test
    void gettingAnIngredientByIdShouldReturnTheCorrectIngredientWithoutErrors() {
        ResponseEntity<EntityModel<Ingredient>> responseEntity = this.templateWrapper
                .getOne(this.savedIngredient.getId());

        assertThat(responseEntity.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);

        Ingredient ingredientFromResponse = responseEntity.getBody().getContent();

        assertThat(ingredientFromResponse)
                .hasName(this.savedIngredient.getName())
                .hasBrand(this.savedIngredient.getBrand())
                .hasNutritionFacts(this.savedIngredient.getNutritionFacts());
    }

    @Test
    void postingAnIngredientShouldCorrectlyPersistItsAttributes() throws URISyntaxException {
        Ingredient referenceIngredient = TestIngredientFactory.withoutReferencePrices();
        ResponseEntity<EntityModel<Ingredient>> responseEntity = this.templateWrapper
                .post(TestIngredientFactory.withoutReferencePrices());

        assertThat(responseEntity.getStatusCode()).isEqualByComparingTo(HttpStatus.CREATED);

        Ingredient createdIngredient = responseEntity.getBody().getContent();

        assertThat(createdIngredient)
                .hasName(referenceIngredient.getName())
                .hasBrand(referenceIngredient.getBrand())
                .hasNutritionFacts(referenceIngredient.getNutritionFacts());
    }

    @Test
    void puttingAnIngredientShouldCorrectlyPersistItsAttributes() throws URISyntaxException {
        Ingredient ingredient = TestIngredientFactory.withoutReferencePrices();
        ingredient.setId(this.savedIngredient.getId());
        ingredient.setName("a");
        ingredient.setBrand("b");
        ingredient.getNutritionFacts().setCalories(1000);

        ResponseEntity<EntityModel<Ingredient>> responseEntity = this.templateWrapper
                .put(ingredient);

        assertThat(responseEntity.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);

        Ingredient updatedIngredient = responseEntity.getBody().getContent();

        assertThat(updatedIngredient)
                .hasName(ingredient.getName())
                .hasBrand(ingredient.getBrand())
                .hasNutritionFacts(ingredient.getNutritionFacts());
    }

    @Test
    void deletingAnIngredientShouldRemoveItFromDatabase() {
        ResponseEntity<EntityModel<Ingredient>> responseEntity = this.templateWrapper
                .delete(this.savedIngredient.getId());

        assertThat(responseEntity.getStatusCode()).isEqualByComparingTo(HttpStatus.NO_CONTENT);
        assertThat(this.repository.count()).isEqualTo(0);
    }
}
