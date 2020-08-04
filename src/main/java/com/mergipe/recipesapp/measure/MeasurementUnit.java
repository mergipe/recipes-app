package com.mergipe.recipesapp.measure;

public enum MeasurementUnit {

    GRAM("g"),
    MILLILITER("ml"),
    TEASPOON("colher de chá"),
    TABLESPOON("colher de sopa"),
    CUP("xícara"),
    UNIT("unidade");

    private final String label;

    MeasurementUnit(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
