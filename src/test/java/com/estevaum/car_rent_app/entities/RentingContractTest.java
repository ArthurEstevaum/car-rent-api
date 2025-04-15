package com.estevaum.car_rent_app.entities;

import com.estevaum.car_rent_app.enums.UserTypes;
import com.estevaum.car_rent_app.exceptions.InvalidDateException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.time.LocalDate;

public class RentingContractTest {
    @Test
    void shouldInstantiateCarContract_whenConstructorParamsAreValid() {
        CarVariant carModel = new CarVariant("Eclipse", "Mitsubishi", "Sportive", 2015, BigDecimal.valueOf(250));
        Car car = new Car("KHZ1T89", true);
        car.setCarVariant(carModel);
        User user = new User("username", "password", "admin@example.com", "examplephonenumber", UserTypes.personal);

        RentingContract contract = new RentingContract(car, LocalDate.now().plusWeeks(1), LocalDate.now(), user);

        assertThat(contract).isNotNull();
    }

    @Test
    void shouldThrowInvalidDateException_whenEndDateIsBeforStartDate() {
        CarVariant carModel = new CarVariant("Eclipse", "Mitsubishi", "Sportive", 2015, BigDecimal.valueOf(250));
        Car car = new Car("KHZ1T89", true);
        car.setCarVariant(carModel);
        User user = new User("username", "password", "admin@example.com", "examplephonenumber", UserTypes.personal);

        InvalidDateException exception = assertThrows(InvalidDateException.class, () -> new RentingContract(car, LocalDate.now().minusWeeks(1), LocalDate.now(), user));

        assertThat(exception.getMessage()).isEqualTo("A data de término não pode ser anterior à data de início.");
    }

    @Test
    void shouldReturnTrue_whenContractIsCurrent() {
        CarVariant carModel = new CarVariant("Eclipse", "Mitsubishi", "Sportive", 2015, BigDecimal.valueOf(250));
        Car car = new Car("KHZ1T89", true);
        car.setCarVariant(carModel);
        User user = new User("username", "password", "admin@example.com", "examplephonenumber", UserTypes.personal);

        RentingContract contract = new RentingContract(car, LocalDate.now().plusWeeks(1), LocalDate.now(), user);

        assertThat(contract.isCurrentContract()).isEqualTo(true);
    }

    @Test
    void shouldReturnFalse_whenContractIsNotCurrent() {
        CarVariant carModel = new CarVariant("Eclipse", "Mitsubishi", "Sportive", 2015, BigDecimal.valueOf(250));
        Car car = new Car("KHZ1T89", true);
        car.setCarVariant(carModel);
        User user = new User("username", "password", "admin@example.com", "examplephonenumber", UserTypes.personal);

        RentingContract contract = new RentingContract(car, LocalDate.now().minusWeeks(1), LocalDate.now().minusWeeks(2), user);

        assertThat(contract.isCurrentContract()).isEqualTo(false);
    }

    @Test
    void shouldReturnCorrectContractPrice_accordingToRentalPeriod() {
        CarVariant carModel = new CarVariant("Eclipse", "Mitsubishi", "Sportive", 2015, BigDecimal.valueOf(250));
        Car car = new Car("KHZ1T89", true);
        car.setCarVariant(carModel);
        User user = new User("username", "password", "admin@example.com", "examplephonenumber", UserTypes.personal);

        RentingContract contract = new RentingContract(car, LocalDate.now().plusWeeks(1), LocalDate.now(), user);

        assertThat(contract.getContractTotalPrice()).isEqualTo(BigDecimal.valueOf(1750));
    }
}
