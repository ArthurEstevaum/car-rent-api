package com.estevaum.car_rent_app.DTO.Contracts;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ContractInfoDTO(@NotNull Long contractId, @NotNull LocalDate startDate, @NotNull LocalDate endDate,
                              @NotNull BigDecimal contractTotalPrice, @NotNull Boolean active,
                              @Valid CarContractInfoDTO carInfo, @Valid ContractUserInfoDTO userInfo) {
}