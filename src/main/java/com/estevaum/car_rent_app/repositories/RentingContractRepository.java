package com.estevaum.car_rent_app.repositories;

import com.estevaum.car_rent_app.entities.RentingContract;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RentingContractRepository extends JpaRepository<RentingContract, Long> {
    public List<RentingContract> findByUserUsername(String username);

    public List<RentingContract> findByCarId(Long id);

    public Boolean existsByCarId(Long id);
}
