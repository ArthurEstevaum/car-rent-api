package com.estevaum.car_rent_app.repositories;

import com.estevaum.car_rent_app.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Long, User> {
}
