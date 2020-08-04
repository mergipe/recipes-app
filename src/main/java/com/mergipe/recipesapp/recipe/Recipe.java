package com.mergipe.recipesapp.recipe;

import com.mergipe.recipesapp.ingredient.NutritionFacts;

import javax.persistence.*;
import java.util.List;

@Entity
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "numberOfPortions", column = @Column(nullable = true))
    })
    private Yield yield;

    @OneToMany
    @JoinColumn(name = "recipe_id", nullable = false)
    private List<RecipeItem> recipeItemList;

    @Transient
    private NutritionFacts nutritionFacts;

    @Lob
    private String instructions;
}
