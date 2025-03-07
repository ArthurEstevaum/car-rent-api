package com.estevaum.car_rent_app;

import com.estevaum.car_rent_app.entities.Permission;
import com.estevaum.car_rent_app.entities.User;
import com.estevaum.car_rent_app.enums.UserTypes;
import com.estevaum.car_rent_app.repositories.PermissionRepository;
import com.estevaum.car_rent_app.repositories.UserRepository;
import com.estevaum.car_rent_app.services.AuthorizationServerService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public class SeedApplication implements CommandLineRunner {

    @Value("${security.credentials.admin-username}")
    private String adminUsername;

    @Value("${security.credentials.admin-password}")
    private String adminPassword;
    
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    
    @Autowired
    private UserRepository repository;
    @Autowired
    private PermissionRepository permissionRepository;
    
    @Autowired
    private AuthorizationServerService authorizationServerService;
    
    @Override
    @Transactional
    public void run(String... args) throws Exception {

        Permission userPermission = new Permission();
        userPermission.setName("user");
        Permission adminPermission = new Permission();
        adminPermission.setName("admin");
        permissionRepository.saveAll(List.of(userPermission, adminPermission));

        String encryptedPassword = passwordEncoder.encode(adminPassword);
        User user = new User(adminUsername, encryptedPassword, "admin@example.com", "examplephonenumber", UserTypes.personal);
        user.addPermission(adminPermission);
        repository.save(user);

        var token = authorizationServerService.createAccessToken(user.getUsername(), adminPassword);

        System.out.println(token);
    }
}
