package com.mergipe.recipesapp.ingredient;

import com.mergipe.recipesapp.RestClientTestConfiguration;
import com.mergipe.recipesapp.TestRestTemplateWrapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
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

    private TestRestTemplateWrapper<Ingredient> templateWrapper;
    private Ingredient testIngredient;

    IngredientRestClientTest(@Autowired TestRestTemplate template) {
        this.templateWrapper = new TestRestTemplateWrapper<>(
                Ingredient.class,
                template,
                "ingredients"
        );
    }

    @BeforeEach
    void createTestIngredient() {
        this.testIngredient = TestIngredientFactory.withoutReferencePrices();
    }

    @AfterEach
    void cleanRepository() {
        this.repository.deleteAll();
    }

    @Nested
    @SpringBootTest
    @AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
    class WhenDatabaseIsEmpty {

        @Nested
        @SpringBootTest
        @AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
        class Post {

            @Test
            void shouldCorrectlyPersistItsAttributes() throws URISyntaxException {
                ResponseEntity<EntityModel<Ingredient>> responseEntity = templateWrapper
                        .post(testIngredient);

                assertThat(responseEntity.getStatusCode()).isEqualByComparingTo(HttpStatus.CREATED);
                assertThat(repository.count()).isEqualTo(1);

                Ingredient createdIngredient = responseEntity.getBody().getContent();

                assertThat(createdIngredient)
                        .hasName(testIngredient.getName())
                        .hasBrand(testIngredient.getBrand())
                        .hasNutritionFacts(testIngredient.getNutritionFacts());
            }
        }
    }

    @Nested
    @SpringBootTest
    @AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
    class WhenDatabaseHasOneIngredient {

        private Ingredient savedIngredient;

        @BeforeEach
        void persistTestIngredient() {
            this.savedIngredient = repository.saveAndFlush(testIngredient);
        }

        @Nested
        @SpringBootTest
        @AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
        class Get {

            @Test
            void shouldCorrectlyReturnAllIngredients() {
                ResponseEntity<PagedModel<Ingredient>> responseEntity = templateWrapper.getAll();

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
                        .hasName(savedIngredient.getName())
                        .hasBrand(savedIngredient.getBrand())
                        .hasNutritionFacts(savedIngredient.getNutritionFacts());
            }

            @Test
            void shouldReturnTheCorrectIngredientGivenItsId() {
                ResponseEntity<EntityModel<Ingredient>> responseEntity = templateWrapper
                        .getOne(savedIngredient.getId());

                assertThat(responseEntity.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);

                Ingredient ingredientFromResponse = responseEntity.getBody().getContent();

                assertThat(ingredientFromResponse)
                        .hasName(savedIngredient.getName())
                        .hasBrand(savedIngredient.getBrand())
                        .hasNutritionFacts(savedIngredient.getNutritionFacts());
            }
        }

        @Nested
        @SpringBootTest
        @AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
        class Put {

            @Test
            void shouldCorrectlyUpdateItsAttributes() throws URISyntaxException {
                savedIngredient.setName("a");
                savedIngredient.setBrand("b");
                savedIngredient.getNutritionFacts().setCalories(1000);

                ResponseEntity<EntityModel<Ingredient>> responseEntity = templateWrapper
                        .put(savedIngredient);

                assertThat(responseEntity.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);

                Ingredient updatedIngredient = responseEntity.getBody().getContent();

                assertThat(updatedIngredient)
                        .hasName(savedIngredient.getName())
                        .hasBrand(savedIngredient.getBrand())
                        .hasNutritionFacts(savedIngredient.getNutritionFacts());
            }
        }

        @Nested
        @SpringBootTest
        @AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
        class Delete {

            @Test
            void shouldRemoveItFromDatabase() {
                ResponseEntity<EntityModel<Ingredient>> responseEntity = templateWrapper
                        .delete(savedIngredient.getId());

                assertThat(responseEntity.getStatusCode())
                        .isEqualByComparingTo(HttpStatus.NO_CONTENT);
                assertThat(repository.count()).isEqualTo(0);
            }
        }
    }
}
