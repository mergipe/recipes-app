package com.mergipe.recipesapp.recipe;

import com.mergipe.recipesapp.ingredient.Ingredient;
import com.mergipe.recipesapp.measure.ScalarQuantity;

import javax.persistence.*;

@Entity
public class RecipeItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Recipe recipe;

    @OneToOne
    @JoinColumn(nullable = false)
    private Ingredient ingredient;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(
                    name = "magnitude",
                    column = @Column(name = "quantity", nullable = false)
            ),
            @AttributeOverride(
                    name = "measurementUnit",
                    column = @Column(name = "quantity_unit", nullable = false)
            )
    })
    private ScalarQuantity quantity;

    public RecipeItem() {
    }

    public RecipeItem(Recipe recipe, Ingredient ingredient, ScalarQuantity quantity) {
        this.recipe = recipe;
        this.ingredient = ingredient;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public ScalarQuantity getQuantity() {
        return quantity;
    }

    public void setQuantity(ScalarQuantity quantity) {
        this.quantity = quantity;
    }
}
