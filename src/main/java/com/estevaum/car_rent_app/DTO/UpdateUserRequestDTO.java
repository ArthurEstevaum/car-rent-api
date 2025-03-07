package com.estevaum.car_rent_app.DTO;

import com.estevaum.car_rent_app.enums.UserTypes;
import jakarta.validation.constraints.Email;

public record UpdateUserRequestDTO(String username, @Email String email, String phoneNumber, UserTypes userType) {
}
