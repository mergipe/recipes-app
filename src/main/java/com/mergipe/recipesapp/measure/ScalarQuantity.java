package com.mergipe.recipesapp.measure;

import javax.persistence.*;

@Embeddable
public class ScalarQuantity {

    @Column(nullable = false)
    private double magnitude;

    @Column(nullable = false)
    private MeasurementUnit measurementUnit;

    public double getMagnitude() {
        return magnitude;
    }

    public void setMagnitude(double magnitude) {
        this.magnitude = magnitude;
    }

    public MeasurementUnit getMeasurementUnit() {
        return measurementUnit;
    }

    public void setMeasurementUnit(MeasurementUnit measurementUnit) {
        this.measurementUnit = measurementUnit;
    }
}
