package com.estevaum.car_rent_app.services;

import com.estevaum.car_rent_app.DTO.UpdateUserRequestDTO;
import com.estevaum.car_rent_app.entities.User;
import com.estevaum.car_rent_app.exceptions.UserNotFoundException;
import com.estevaum.car_rent_app.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserManagementService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User searchUserByUsername(String username) {

        return userRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);
    }

    public User searchUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
    }

    public User updateUserInfo(Long id, UpdateUserRequestDTO requestData) {
        User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);

        Optional.ofNullable(requestData.username()).ifPresent(user::setUsername);
        Optional.ofNullable(requestData.email()).ifPresent(user::setEmail);
        Optional.ofNullable(requestData.phoneNumber()).ifPresent(user::setPhoneNumber);
        Optional.ofNullable(requestData.userType()).ifPresent(user::setUserType);
        return userRepository.save(user);
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
