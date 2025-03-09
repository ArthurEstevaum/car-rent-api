package com.estevaum.car_rent_app.repositories;

import com.estevaum.car_rent_app.entities.RentingContract;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentingContractRepository extends JpaRepository<RentingContract, Long> {
}
