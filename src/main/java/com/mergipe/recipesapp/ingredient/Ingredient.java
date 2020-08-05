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
    @JoinColumn(name = "ingredient_id", nullable = false)
    private List<ReferencePrice> referencePriceList;

    @Embedded
    private NutritionFacts nutritionFacts;
}
