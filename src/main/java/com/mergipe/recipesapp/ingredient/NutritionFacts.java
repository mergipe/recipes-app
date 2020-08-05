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

    public ScalarQuantity getPrimaryServingSize() {
        return primaryServingSize;
    }

    public void setPrimaryServingSize(ScalarQuantity primaryServingSize) {
        this.primaryServingSize = primaryServingSize;
    }

    public ScalarQuantity getSecondaryServingSize() {
        return secondaryServingSize;
    }

    public void setSecondaryServingSize(ScalarQuantity secondaryServingSize) {
        this.secondaryServingSize = secondaryServingSize;
    }

    public double getCalories() {
        return calories;
    }

    public void setCalories(double calories) {
        this.calories = calories;
    }

    public double getTotalCarbohydrate() {
        return totalCarbohydrate;
    }

    public void setTotalCarbohydrate(double totalCarbohydrate) {
        this.totalCarbohydrate = totalCarbohydrate;
    }

    public double getProtein() {
        return protein;
    }

    public void setProtein(double protein) {
        this.protein = protein;
    }

    public double getTotalFat() {
        return totalFat;
    }

    public void setTotalFat(double totalFat) {
        this.totalFat = totalFat;
    }

    public double getSaturatedFat() {
        return saturatedFat;
    }

    public void setSaturatedFat(double saturatedFat) {
        this.saturatedFat = saturatedFat;
    }

    public double getTransFat() {
        return transFat;
    }

    public void setTransFat(double transFat) {
        this.transFat = transFat;
    }

    public double getDietaryFiber() {
        return dietaryFiber;
    }

    public void setDietaryFiber(double dietaryFiber) {
        this.dietaryFiber = dietaryFiber;
    }

    public double getSodium() {
        return sodium;
    }

    public void setSodium(double sodium) {
        this.sodium = sodium;
    }
}
