package com.mergipe.recipesapp.ingredient;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String brand;

    @OneToMany(mappedBy = "ingredient", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReferencePrice> referencePrices = new ArrayList<>();

    @Embedded
    private NutritionFacts nutritionFacts;

    public Ingredient() {
    }

    public Ingredient(String name, String brand, NutritionFacts nutritionFacts) {
        this.name = name;
        this.brand = brand;
        this.nutritionFacts = nutritionFacts;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public List<ReferencePrice> getReferencePrices() {
        return referencePrices;
    }

    public void setReferencePrices(List<ReferencePrice> referencePrices) {
        this.referencePrices = referencePrices;
    }

    public NutritionFacts getNutritionFacts() {
        return nutritionFacts;
    }

    public void setNutritionFacts(NutritionFacts nutritionFacts) {
        this.nutritionFacts = nutritionFacts;
    }

    public void addReferencePrice(ReferencePrice referencePrice) {
        this.referencePrices.add(referencePrice);
        referencePrice.setIngredient(this);
    }

    public void removeReferencePrice(ReferencePrice referencePrice) {
        this.referencePrices.remove(referencePrice);
        referencePrice.setIngredient(null);
    }
}
