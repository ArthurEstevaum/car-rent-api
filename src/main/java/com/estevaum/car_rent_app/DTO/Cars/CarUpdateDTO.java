package com.estevaum.car_rent_app.DTO.Cars;

import com.estevaum.car_rent_app.constraints.NullOrPattern;

public record CarUpdateDTO(@NullOrPattern(pattern = "^[A-Z]{3}\\d[A-Z]\\d{2}$") String newLicensePlate, Boolean available) {
}
