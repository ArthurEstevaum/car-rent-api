package com.estevaum.car_rent_app.DTO;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;

public record CreateContractDTO(@NotNull String username, @NotNull Long carId, @Future LocalDate startDate, @Future LocalDate endDate) {
}
