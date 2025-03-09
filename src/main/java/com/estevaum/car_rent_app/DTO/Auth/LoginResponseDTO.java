package com.estevaum.car_rent_app.DTO.Auth;

import jakarta.validation.constraints.NotNull;

public record LoginResponseDTO(@NotNull String accessToken, @NotNull Integer expiresIn) {
}
