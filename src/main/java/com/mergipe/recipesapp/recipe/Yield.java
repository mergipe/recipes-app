package com.mergipe.recipesapp.recipe;

import com.mergipe.recipesapp.measure.ScalarQuantity;

import javax.persistence.*;

@Embeddable
public class Yield {

    private int numberOfPortions;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(
                    name = "magnitude",
                    column = @Column(name = "portion_size")
            ),
            @AttributeOverride(
                    name = "measurementUnit",
                    column = @Column(name = "portion_size_unit")
            )
    })
    @Enumerated(EnumType.STRING)
    private ScalarQuantity portionSize;
}
