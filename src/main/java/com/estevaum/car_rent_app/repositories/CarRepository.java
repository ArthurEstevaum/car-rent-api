package com.estevaum.car_rent_app.repositories;

import com.estevaum.car_rent_app.entities.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car, Long> {
}
