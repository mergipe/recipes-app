package com.mergipe.recipesapp.recipe;

import com.mergipe.recipesapp.ingredient.NutritionFacts;

import javax.persistence.*;
import java.util.ArrayList;
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

    @OneToMany(
            mappedBy = "recipe",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<RecipeItem> recipeItemList = new ArrayList<>();

    @Transient
    private NutritionFacts nutritionFacts;

    @Lob
    private String instructions;

    public Recipe() {
    }

    public Recipe(String name, Yield yield, NutritionFacts nutritionFacts, String instructions) {
        this.name = name;
        this.yield = yield;
        this.nutritionFacts = nutritionFacts;
        this.instructions = instructions;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Yield getYield() {
        return yield;
    }

    public void setYield(Yield yield) {
        this.yield = yield;
    }

    public List<RecipeItem> getRecipeItemList() {
        return recipeItemList;
    }

    public void setRecipeItemList(List<RecipeItem> recipeItemList) {
        this.recipeItemList = recipeItemList;
    }

    public NutritionFacts getNutritionFacts() {
        return nutritionFacts;
    }

    public void setNutritionFacts(NutritionFacts nutritionFacts) {
        this.nutritionFacts = nutritionFacts;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }
}
