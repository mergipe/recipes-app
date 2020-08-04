package com.mergipe.recipesapp.measure;

import javax.persistence.*;

@Embeddable
public class ScalarQuantity {

    @Column(nullable = false)
    private double magnitude;

    @Column(nullable = false)
    private MeasurementUnit measurementUnit;
}
