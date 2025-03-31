package com.estevaum.car_rent_app.DTO;

import com.estevaum.car_rent_app.entities.Permission;
import com.estevaum.car_rent_app.entities.RentingContract;
import com.estevaum.car_rent_app.enums.UserTypes;

import java.util.Set;

public record UserResponseDTO(Long id, String username, String email,
                              String phoneNumber, UserTypes userType,
                              Set<RentingContract> contracts, Set<String> permissions) {
}
