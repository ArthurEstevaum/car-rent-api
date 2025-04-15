package com.estevaum.car_rent_app.entities;

import com.estevaum.car_rent_app.exceptions.InvalidLicensePlateException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CarTest {
    @Test
    void shouldInstantiateCar_whenConstructorParamsAreValid() {
        Car car = new Car("KHZ1T89", true);

        assertThat(car.getAvailable()).isEqualTo(true);
        assertThat(car.getLicensePlate()).isEqualTo("KHZ1T89");

        assertThat(car).isNotNull();
    }

    @Test
    void shouldInstantiateCarWithLegacyLicensePlate_whenConstructorParamsAreValid() {
        Car car = new Car("KHZ-3670", false, true);

        assertThat(car.getAvailable()).isEqualTo(false);
        assertThat(car.getLicensePlate()).isEqualTo("KHZ-3670");

        assertThat(car).isNotNull();
    }

    @Test
    void shouldThrowInvalidLicensePlateException_whenLicensePlateIsInvalid() {
        InvalidLicensePlateException exception = assertThrows(InvalidLicensePlateException.class, () -> new Car("invalidLicenseplate", true));

        assertThat(exception.getMessage()).isEqualTo("O formato fornecido para a placa do carro é invalido.");
    }

    @Test
    void shouldSetCarVariant() {
        CarVariant carModel = new CarVariant("Eclipse", "Mitsubishi", "Sportive", 2015, BigDecimal.valueOf(250));
        Car car = new Car("KHZ1T89", true);

        car.setCarVariant(carModel);

        assertThat(car.getModel()).isEqualTo(carModel);
    }
}
