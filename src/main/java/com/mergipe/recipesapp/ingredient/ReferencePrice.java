package com.mergipe.recipesapp.ingredient;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class ReferencePrice {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String brand;
    private String description;

    @Column(nullable = false)
    private double amount;

    @Column(nullable = false)
    private BigDecimal price;
}
