package com.estevaum.car_rent_app.entities;

import com.estevaum.car_rent_app.enums.UserTypes;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public class UserTest {

    @Test
    void shouldAddPermissionsProperly() {

        Permission permission1 = new Permission();
        Permission permission2 = new Permission();
        permission1.setName("permission1");
        permission2.setName("permission2");

        User user = new User("username", "password", "email@example.com", "examplePhone", UserTypes.business);
        user.addPermission(permission1);
        user.addPermission(permission2);

        assertThat(user.getPermissions()).containsAll(List.of(permission1, permission2));
    }

    @Test
    void shouldReturnTrue_whenThereAreActiveContracts() {
        User user = new User("username", "password", "email@example.com", "examplePhone", UserTypes.business);
        CarVariant carModel = new CarVariant("Eclipse", "Mitsubishi", "Sportive", 2015, BigDecimal.valueOf(250));
        CarVariant carModel2 = new CarVariant("Civic", "Honda", "Sedan", 2018, BigDecimal.valueOf(220));
        Car car = new Car("KHZ1T89", true);
        Car car2 = new Car("KHZ1T86", true);
        RentingContract contract = new RentingContract(car, LocalDate.now().plusWeeks(1), LocalDate.now(), user);
        RentingContract contract2 = new RentingContract(car2, LocalDate.now().minusWeeks(1), LocalDate.now().minusWeeks(2), user);
        contract.setId(1L);
        contract.setId(2L);
        user.setContracts(Set.of(contract, contract2));

        assertThat(user.hasActiveContracts()).isEqualTo(true);
    }

    @Test
    void shouldReturnFalse_whenThereAreNoActiveContracts() {
        User user = new User("username", "password", "email@example.com", "examplePhone", UserTypes.business);
        CarVariant carModel = new CarVariant("Eclipse", "Mitsubishi", "Sportive", 2015, BigDecimal.valueOf(250));
        CarVariant carModel2 = new CarVariant("Civic", "Honda", "Sedan", 2018, BigDecimal.valueOf(220));
        Car car = new Car("KHZ1T89", true);
        Car car2 = new Car("KHZ1T86", true);
        RentingContract contract = new RentingContract(car, LocalDate.now().minusWeeks(1), LocalDate.now().minusWeeks(2), user);
        RentingContract contract2 = new RentingContract(car2, LocalDate.now().minusWeeks(1), LocalDate.now().minusWeeks(2), user);
        contract.setId(1L);
        contract2.setId(2L);
        user.setContracts(Set.of(contract, contract2));

        assertThat(user.hasActiveContracts()).isEqualTo(false);
    }
}
