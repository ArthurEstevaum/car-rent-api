package com.estevaum.car_rent_app.DTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record CarRegistrationDTO(@Pattern(regexp = "([A-Z]{3}\\\\d[A-Z]\\\\d{2}) | ([A-Z]{3}-\\d{4}$)") String licensePlate,
                                 @NotNull Boolean available, @NotNull String modelName) {
}
