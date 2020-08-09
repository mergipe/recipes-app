package com.mergipe.recipesapp.ingredient;

import com.mergipe.recipesapp.measure.ScalarQuantity;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class ReferencePrice {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Ingredient ingredient;

    private String brand;
    private String description;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(
                    name = "magnitude",
                    column = @Column(name = "amount", nullable = false)
            ),
            @AttributeOverride(
                    name = "measurementUnit",
                    column = @Column(name = "amount_unit", nullable = false)
            )
    })
    private ScalarQuantity amount;

    @Column(nullable = false)
    private BigDecimal price;

    public ReferencePrice() {
    }

    public ReferencePrice(Ingredient ingredient, String brand,
                          String description, ScalarQuantity amount,
                          BigDecimal price) {
        this.ingredient = ingredient;
        this.brand = brand;
        this.description = description;
        this.amount = amount;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ScalarQuantity getAmount() {
        return amount;
    }

    public void setAmount(ScalarQuantity amount) {
        this.amount = amount;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
