package com.mergipe.recipesapp.measure;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class ScalarQuantity {

    @Column(nullable = false)
    private double magnitude;

    @Column(nullable = false)
    private MeasurementUnit measurementUnit;

    public ScalarQuantity() {
    }

    public ScalarQuantity(double magnitude, MeasurementUnit measurementUnit) {
        this.magnitude = magnitude;
        this.measurementUnit = measurementUnit;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScalarQuantity that = (ScalarQuantity) o;
        return Double.compare(that.magnitude, magnitude) == 0 &&
                measurementUnit == that.measurementUnit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(magnitude, measurementUnit);
    }
}
