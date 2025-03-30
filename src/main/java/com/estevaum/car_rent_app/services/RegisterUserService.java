package com.estevaum.car_rent_app.services;

import com.estevaum.car_rent_app.DTO.Auth.RegisterUserRequestDTO;
import com.estevaum.car_rent_app.entities.Permission;
import com.estevaum.car_rent_app.entities.User;
import com.estevaum.car_rent_app.exceptions.UserAlreadyExistsException;
import com.estevaum.car_rent_app.repositories.PermissionRepository;
import com.estevaum.car_rent_app.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegisterUserService {

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public void register(RegisterUserRequestDTO requestData) throws UserAlreadyExistsException {
        Permission permission = permissionRepository.findById(1L).orElseThrow();

        Boolean userAlreadyExists = userRepository.existsByUsername(requestData.username());

        if(userAlreadyExists) {
            throw new UserAlreadyExistsException("Erro: o usuário já existe.");
        }

        User user = new User(requestData.username(), passwordEncoder.encode(requestData.password()), requestData.email(), requestData.phoneNumber(), requestData.userType());
        user.addPermission(permission);
        userRepository.save(user);
    }
}
