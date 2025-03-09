package com.estevaum.car_rent_app.DTO.Contracts;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record CarContractInfoDTO(@Pattern(regexp = "([A-Z]{3}\\\\d[A-Z]\\\\d{2}) | ([A-Z]{3}-\\d{4}$)") String licensePlate,
                                 @NotNull Long id, @NotNull String manufacturer, @NotNull String model,
                                 @NotNull String category, @NotNull Integer modelYear) {
}
