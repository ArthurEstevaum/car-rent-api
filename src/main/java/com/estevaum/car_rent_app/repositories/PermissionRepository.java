package com.estevaum.car_rent_app.repositories;

import com.estevaum.car_rent_app.entities.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission, Long> {
}
