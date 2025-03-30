package com.estevaum.car_rent_app.controllers;

import com.estevaum.car_rent_app.DTO.Auth.LoginRequestDTO;
import com.estevaum.car_rent_app.DTO.Auth.LoginResponseDTO;
import com.estevaum.car_rent_app.DTO.Auth.RegisterUserRequestDTO;
import com.estevaum.car_rent_app.DTO.Auth.RegisterUserResponseDTO;
import com.estevaum.car_rent_app.entities.Permission;
import com.estevaum.car_rent_app.entities.User;
import com.estevaum.car_rent_app.exceptions.UserAlreadyExistsException;
import com.estevaum.car_rent_app.repositories.PermissionRepository;
import com.estevaum.car_rent_app.repositories.UserRepository;
import com.estevaum.car_rent_app.services.AuthorizationServerService;
import com.estevaum.car_rent_app.services.RegisterUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/auth")
@RestController
public class AuthenticationController {

    @Autowired
    private AuthorizationServerService authService;

    @Autowired
    private RegisterUserService registerUserService;

    @PostMapping(value = "/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO requestData) throws Exception {
        String accessToken = authService.createAccessToken(requestData.username(), requestData.password());

        return ResponseEntity.ok(new LoginResponseDTO(accessToken, 300));
    }

    @PostMapping(value = "/register")
    public ResponseEntity<RegisterUserResponseDTO> register(@Valid @RequestBody RegisterUserRequestDTO requestData) {
        User user = registerUserService.register(requestData);

        return ResponseEntity.ok(new RegisterUserResponseDTO("Usuário criado com sucesso", user.getUsername()));
    }
}
