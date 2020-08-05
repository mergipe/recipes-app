package com.mergipe.recipesapp.ingredient;

import com.mergipe.recipesapp.measure.ScalarQuantity;

import javax.persistence.*;

@Embeddable
public class NutritionFacts {

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(
                    name = "magnitude",
                    column = @Column(name = "primary_serving_size")
            ),
            @AttributeOverride(
                    name = "measurementUnit",
                    column = @Column(name = "primary_serving_size_unit")
            )
    })
    private ScalarQuantity primaryServingSize;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(
                    name = "magnitude",
                    column = @Column(name = "secondary_serving_size")
            ),
            @AttributeOverride(
                    name = "measurementUnit",
                    column = @Column(name = "secondary_serving_size_unit")
            )
    })
    @Enumerated(EnumType.STRING)
    private ScalarQuantity secondaryServingSize;

    @Column(nullable = false, columnDefinition = "double default 0")
    private double calories;

    @Column(nullable = false, columnDefinition = "double default 0")
    private double totalCarbohydrate;

    @Column(nullable = false, columnDefinition = "double default 0")
    private double protein;

    @Column(nullable = false, columnDefinition = "double default 0")
    private double totalFat;

    @Column(nullable = false, columnDefinition = "double default 0")
    private double saturatedFat;

    @Column(nullable = false, columnDefinition = "double default 0")
    private double transFat;

    @Column(nullable = false, columnDefinition = "double default 0")
    private double dietaryFiber;

    @Column(nullable = false, columnDefinition = "double default 0")
    private double sodium;
}
