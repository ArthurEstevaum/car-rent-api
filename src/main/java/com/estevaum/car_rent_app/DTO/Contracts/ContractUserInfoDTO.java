package com.estevaum.car_rent_app.DTO.Contracts;

import com.estevaum.car_rent_app.enums.UserTypes;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record ContractUserInfoDTO(@NotNull Long id, @NotNull String username, @Email String email,
                                  @NotNull String phoneNumber, @NotNull UserTypes userType) {
}
