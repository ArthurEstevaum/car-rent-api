package com.estevaum.car_rent_app.repositories;

import com.estevaum.car_rent_app.entities.Car;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CarRepository extends JpaRepository<Car, Long> {
    List<Car> findByAvailableTrue();

    List<Car> findByModelName(String name);
    List<Car> findByModelNameAndAvailableTrue(String name);

    List<Car> findByModelManufacturer(String manufacturer);
    List<Car> findByModelManufacturerAndAvailableTrue(String manufacturer);

    List<Car> findByModelCategory(String category);
    List<Car> findByModelCategoryAndAvailableTrue(String category);

    List<Car> findByModelModelYear(Integer modelYear);
    List<Car> findByModelModelYearAndAvailableTrue(Integer modelYear);
}
