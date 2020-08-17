package com.mergipe.recipesapp.ingredient;

import com.mergipe.recipesapp.measure.ScalarQuantity;

import javax.persistence.*;
import java.util.Objects;

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

    public NutritionFacts() {
    }

    public NutritionFacts(ScalarQuantity primaryServingSize, ScalarQuantity secondaryServingSize,
                          double calories, double totalCarbohydrate, double protein,
                          double totalFat, double saturatedFat, double transFat,
                          double dietaryFiber, double sodium) {
        this.primaryServingSize = primaryServingSize;
        this.secondaryServingSize = secondaryServingSize;
        this.calories = calories;
        this.totalCarbohydrate = totalCarbohydrate;
        this.protein = protein;
        this.totalFat = totalFat;
        this.saturatedFat = saturatedFat;
        this.transFat = transFat;
        this.dietaryFiber = dietaryFiber;
        this.sodium = sodium;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NutritionFacts that = (NutritionFacts) o;
        return Double.compare(that.calories, calories) == 0 &&
                Double.compare(that.totalCarbohydrate, totalCarbohydrate) == 0 &&
                Double.compare(that.protein, protein) == 0 &&
                Double.compare(that.totalFat, totalFat) == 0 &&
                Double.compare(that.saturatedFat, saturatedFat) == 0 &&
                Double.compare(that.transFat, transFat) == 0 &&
                Double.compare(that.dietaryFiber, dietaryFiber) == 0 &&
                Double.compare(that.sodium, sodium) == 0 &&
                Objects.equals(primaryServingSize, that.primaryServingSize) &&
                Objects.equals(secondaryServingSize, that.secondaryServingSize);
    }

    @Override
    public int hashCode() {
        return Objects.hash(primaryServingSize, secondaryServingSize, calories, totalCarbohydrate,
                protein, totalFat, saturatedFat, transFat, dietaryFiber, sodium);
    }

    @Override
    public String toString() {
        return "NutritionFacts{" +
                "primaryServingSize=" + primaryServingSize +
                ", secondaryServingSize=" + secondaryServingSize +
                ", calories=" + calories +
                ", totalCarbohydrate=" + totalCarbohydrate +
                ", protein=" + protein +
                ", totalFat=" + totalFat +
                ", saturatedFat=" + saturatedFat +
                ", transFat=" + transFat +
                ", dietaryFiber=" + dietaryFiber +
                ", sodium=" + sodium +
                '}';
    }
}
