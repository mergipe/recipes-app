package com.mergipe.recipesapp.ingredient;

import com.mergipe.recipesapp.measure.ScalarQuantity;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class ReferencePrice {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
