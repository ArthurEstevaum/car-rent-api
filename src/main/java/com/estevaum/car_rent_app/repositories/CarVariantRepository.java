package com.estevaum.car_rent_app.repositories;

import com.estevaum.car_rent_app.entities.CarVariant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CarVariantRepository extends JpaRepository<CarVariant, Long> {
    public Optional<CarVariant> findByName(String name);
}
