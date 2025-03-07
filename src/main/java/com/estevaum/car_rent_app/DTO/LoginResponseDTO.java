package com.estevaum.car_rent_app.DTO;

import jakarta.validation.constraints.NotNull;

public record LoginResponseDTO(@NotNull String accessToken, @NotNull Integer expiresIn) {
}
