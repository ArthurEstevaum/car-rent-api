package com.estevaum.car_rent_app.DTO;

import java.math.BigDecimal;

public record CarShortInfoDTO(Long id, Boolean available, String modelName, String manufacturer, String category, Integer modelYear, BigDecimal rentPrice) {
}
