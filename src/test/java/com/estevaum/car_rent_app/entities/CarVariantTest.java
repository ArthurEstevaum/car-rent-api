package com.estevaum.car_rent_app.entities;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.math.BigDecimal;
import java.util.Set;

public class CarVariantTest {
    @Test
    void shouldInstantiateCarVariant_whenConstructorParamsAreValid() {
        CarVariant carVariant = new CarVariant("Eclipse", "Mitsubishi", "Sportive", 2015, BigDecimal.valueOf(250));

        assertThat(carVariant.getName()).isEqualTo("Eclipse");
        assertThat(carVariant.getManufacturer()).isEqualTo("Mitsubishi");
        assertThat(carVariant.getCategory()).isEqualTo("Sportive");
        assertThat(carVariant.getModelYear()).isEqualTo(2015);
        assertThat(carVariant.getRentPrice()).isEqualTo(BigDecimal.valueOf(250));

        assertThat(carVariant).isNotNull();
    }
}
