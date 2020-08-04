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

    private double calorie;
    private double carbohydrate;
    private double protein;
    private double fat;
}
