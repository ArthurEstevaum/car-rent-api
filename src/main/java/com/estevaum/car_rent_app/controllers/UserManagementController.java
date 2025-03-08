package com.estevaum.car_rent_app.controllers;

import com.estevaum.car_rent_app.DTO.UpdateUserRequestDTO;
import com.estevaum.car_rent_app.entities.User;
import com.estevaum.car_rent_app.exceptions.UserNotFoundException;
import com.estevaum.car_rent_app.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping(value = "/users")
@RestController
public class UserManagementController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/")
    public ResponseEntity<List<User>> usersList() {
        return ResponseEntity.ok(userRepository.findAll());
    }

    @GetMapping("/{username}")
    public ResponseEntity<User> searchUserByUsername(@PathVariable String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException("Usuário não encontrado."));

        return ResponseEntity.ok(user);
    }

    @GetMapping("/{email}")
    public ResponseEntity<User> searchUserByEmail(@PathVariable String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("Usuário não encontrado"));

        return ResponseEntity.ok(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Long id, @RequestBody UpdateUserRequestDTO requestData) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("Usuário não encontrado"));

        Optional.ofNullable(requestData.username()).ifPresent(user::setUsername);
        Optional.ofNullable(requestData.email()).ifPresent(user::setEmail);
        Optional.ofNullable(requestData.phoneNumber()).ifPresent(user::setPhoneNumber);
        Optional.ofNullable(requestData.userType()).ifPresent(user::setUserType);
        userRepository.save(user);

        return ResponseEntity.ok("Usuário atualizado com sucesso.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);

        return ResponseEntity.ok("Usuário removido.");
    }
}
