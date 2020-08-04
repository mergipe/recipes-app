package com.mergipe.recipesapp.ingredient;

import javax.persistence.*;
import java.util.List;

@Entity
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToMany
    @JoinColumn(name = "ingredient_id")
    private List<ReferencePrice> referencePriceList;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "calorie", column = @Column(nullable = true)),
            @AttributeOverride(name = "carbohydrate", column = @Column(nullable = true)),
            @AttributeOverride(name = "protein", column = @Column(nullable = true)),
            @AttributeOverride(name = "fat", column = @Column(nullable = true))
    })
    private NutritionFacts nutritionFacts;
}
