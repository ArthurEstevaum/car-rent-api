package com.estevaum.car_rent_app.DTO;

import jakarta.validation.constraints.NotNull;

public record LoginRequestDTO(@NotNull String username, @NotNull String password) {
}
