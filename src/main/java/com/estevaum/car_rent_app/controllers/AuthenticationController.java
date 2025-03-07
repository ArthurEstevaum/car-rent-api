package com.estevaum.car_rent_app.controllers;

import com.estevaum.car_rent_app.DTO.LoginRequestDTO;
import com.estevaum.car_rent_app.DTO.LoginResponseDTO;
import com.estevaum.car_rent_app.DTO.RegisterUserRequestDTO;
import com.estevaum.car_rent_app.DTO.RegisterUserResponseDTO;
import com.estevaum.car_rent_app.entities.Permission;
import com.estevaum.car_rent_app.entities.User;
import com.estevaum.car_rent_app.exceptions.UserAlreadyExistsException;
import com.estevaum.car_rent_app.repositories.PermissionRepository;
import com.estevaum.car_rent_app.repositories.UserRepository;
import com.estevaum.car_rent_app.services.AuthorizationServerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

    @Autowired
    private AuthorizationServerService authService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping(value = "/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO requestData) throws Exception {
        String accessToken = authService.createAccessToken(requestData.username(), requestData.password());

        return ResponseEntity.ok(new LoginResponseDTO(accessToken, 300));
    }

    @PostMapping(value = "/register")
    public ResponseEntity<RegisterUserResponseDTO> register(@Valid @RequestBody RegisterUserRequestDTO requestData) throws Exception {
        Permission permission = permissionRepository.findById(1L).orElseThrow();

        Boolean userAlreadyExists = userRepository.existsByUsername(requestData.username());

        if(userAlreadyExists) {
            throw new UserAlreadyExistsException("Erro na requisição: o usuário já existe.");
        }

        User user = new User(requestData.username(), passwordEncoder.encode(requestData.password()), requestData.email(), requestData.phoneNumber(), requestData.userType());
        user.addPermission(permission);
        userRepository.save(user);

        return ResponseEntity.ok(new RegisterUserResponseDTO("Usuário criado com sucesso"));
    }
}
