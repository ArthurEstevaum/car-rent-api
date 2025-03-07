package com.estevaum.car_rent_app.DTO;

import com.estevaum.car_rent_app.enums.UserTypes;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record RegisterUserRequestDTO(@NotNull String username, @NotNull String password, @Email String email, @NotNull String phoneNumber, UserTypes userType) {
}
