package com.estevaum.car_rent_app.services;

import com.estevaum.car_rent_app.DTO.UpdateUserRequestDTO;
import com.estevaum.car_rent_app.DTO.UserResponseDTO;
import com.estevaum.car_rent_app.entities.Permission;
import com.estevaum.car_rent_app.entities.User;
import com.estevaum.car_rent_app.exceptions.UserNotFoundException;
import com.estevaum.car_rent_app.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class UserManagementService {

    @Autowired
    private UserRepository userRepository;

    private final Function<User, UserResponseDTO> convertToDTO = user -> {
        Set<String> permissions = user.getPermissions().stream().map(Permission::getName).collect(Collectors.toSet());
        return new UserResponseDTO(user.getId(), user.getUsername(), user.getEmail(), user.getPhoneNumber(), user.getUserType(), user.getContracts(), permissions);
    };

    public List<UserResponseDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(convertToDTO).toList();
    }

    public UserResponseDTO searchUserByUsername(String username) {

        User user = userRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);
        return convertToDTO.apply(user);
    }

    public UserResponseDTO searchUserByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
        return convertToDTO.apply(user);
    }

    public UserResponseDTO updateUserInfo(Long id, UpdateUserRequestDTO requestData) {
        User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);

        Optional.ofNullable(requestData.username()).ifPresent(user::setUsername);
        Optional.ofNullable(requestData.email()).ifPresent(user::setEmail);
        Optional.ofNullable(requestData.phoneNumber()).ifPresent(user::setPhoneNumber);
        Optional.ofNullable(requestData.userType()).ifPresent(user::setUserType);
        return convertToDTO.apply(userRepository.save(user));
    }

    public boolean deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}
