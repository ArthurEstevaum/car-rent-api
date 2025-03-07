package com.estevaum.car_rent_app.repositories;

import com.estevaum.car_rent_app.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    public Optional<User> findByUsername(String username);

    public Optional<User> findByEmail(String email);

    public Boolean existsByUsername(String username);
}
