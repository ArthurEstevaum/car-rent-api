package com.estevaum.car_rent_app.repositories;

import com.estevaum.car_rent_app.entities.CarVariant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarVariantRepository extends JpaRepository<CarVariant, Long> {
}
