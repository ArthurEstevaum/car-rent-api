package com.estevaum.car_rent_app.controllers;

import com.estevaum.car_rent_app.DTO.UpdateUserRequestDTO;
import com.estevaum.car_rent_app.DTO.UserResponseDTO;
import com.estevaum.car_rent_app.entities.User;
import com.estevaum.car_rent_app.services.UserManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(value = "/users")
@RestController
public class UserManagementController {

    @Autowired
    private UserManagementService managementService;

    @GetMapping("/")
    public ResponseEntity<List<UserResponseDTO>> usersList() {
        return ResponseEntity.ok(managementService.getAllUsers());
    }

    @GetMapping("/name/{username}")
    public ResponseEntity<UserResponseDTO> searchUserByUsername(@PathVariable String username) {
        UserResponseDTO user = managementService.searchUserByUsername(username);

        return ResponseEntity.ok(user);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserResponseDTO> searchUserByEmail(@PathVariable String email) {
        UserResponseDTO user = managementService.searchUserByEmail(email);

        return ResponseEntity.ok(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Long id, @RequestBody UpdateUserRequestDTO requestData) {
        UserResponseDTO user = managementService.updateUserInfo(id, requestData);

        return ResponseEntity.ok("Usuário " + user.username() + " atualizado com sucesso.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        if (managementService.deleteUser(id)) {
            return ResponseEntity.ok("Usuário removido.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário a ser removido não encontrado");
        }
    }
}
