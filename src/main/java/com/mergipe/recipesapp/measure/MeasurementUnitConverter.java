package com.mergipe.recipesapp.measure;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class MeasurementUnitConverter implements AttributeConverter<MeasurementUnit, String> {

    @Override
    public String convertToDatabaseColumn(MeasurementUnit measurementUnit) {
        if (measurementUnit == null) {
            return null;
        }

        return measurementUnit.name();
    }

    @Override
    public MeasurementUnit convertToEntityAttribute(String s) {
        if (s == null) {
            return null;
        }

        return MeasurementUnit.valueOf(s);
    }
}
