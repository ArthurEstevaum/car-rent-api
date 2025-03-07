package com.estevaum.car_rent_app.DTO;

import com.estevaum.car_rent_app.entities.User;

import java.util.List;

public record GetUsersResponseDTO(List<User> usersList) {
}
