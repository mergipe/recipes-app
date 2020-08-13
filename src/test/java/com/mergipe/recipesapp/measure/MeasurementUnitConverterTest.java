package com.mergipe.recipesapp.measure;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MeasurementUnitConverterTest {

    private final MeasurementUnitConverter converter =
            new MeasurementUnitConverter();

    @Test
    void testConvertToDatabaseColumn() {
        assertThat(converter.convertToDatabaseColumn(null))
                .isNull();

        for (MeasurementUnit measurementUnit : MeasurementUnit.values()) {
            assertThat(converter.convertToDatabaseColumn(measurementUnit))
                    .isEqualTo(measurementUnit.name());
        }
    }

    @Test
    void testConvertToEntityAttribute() {
        assertThat(converter.convertToEntityAttribute(null))
                .isNull();

        for (MeasurementUnit measurementUnit : MeasurementUnit.values()) {
            String measurementUnitName = measurementUnit.name();
            assertThat(converter.convertToEntityAttribute(measurementUnitName))
                    .isEqualByComparingTo(MeasurementUnit.valueOf(measurementUnitName));
        }
    }
}
