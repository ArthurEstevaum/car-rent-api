package com.estevaum.car_rent_app.DTO.Contracts;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record CreateContractDTO(@NotNull String username, @NotNull Long carId, @Future LocalDate startDate, @Future LocalDate endDate) {
}
