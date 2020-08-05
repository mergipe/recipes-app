package com.mergipe.recipesapp.recipe;

import com.mergipe.recipesapp.ingredient.Ingredient;
import com.mergipe.recipesapp.measure.ScalarQuantity;

import javax.persistence.*;

@Entity
public class RecipeItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

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
}
