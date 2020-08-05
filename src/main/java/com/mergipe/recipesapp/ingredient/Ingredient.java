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

    public List<ReferencePrice> getReferencePriceList() {
        return referencePriceList;
    }

    public void setReferencePriceList(List<ReferencePrice> referencePriceList) {
        this.referencePriceList = referencePriceList;
    }

    public NutritionFacts getNutritionFacts() {
        return nutritionFacts;
    }

    public void setNutritionFacts(NutritionFacts nutritionFacts) {
        this.nutritionFacts = nutritionFacts;
    }
}
