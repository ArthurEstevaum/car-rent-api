package com.estevaum.car_rent_app;

import com.estevaum.car_rent_app.entities.Car;
import com.estevaum.car_rent_app.entities.CarVariant;
import com.estevaum.car_rent_app.entities.Permission;
import com.estevaum.car_rent_app.entities.User;
import com.estevaum.car_rent_app.enums.UserTypes;
import com.estevaum.car_rent_app.repositories.CarRepository;
import com.estevaum.car_rent_app.repositories.CarVariantRepository;
import com.estevaum.car_rent_app.repositories.PermissionRepository;
import com.estevaum.car_rent_app.repositories.UserRepository;
import com.estevaum.car_rent_app.services.AuthorizationServerService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
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

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private CarVariantRepository carVariantRepository;
    
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

        CarVariant carModel = new CarVariant("Eclipse", "Mitsubishi", "Esportivo", 2015, BigDecimal.valueOf(250));
        CarVariant car2 = new CarVariant("Civic", "Honda", "Sedan", 2018, BigDecimal.valueOf(220));
        CarVariant car3 = new CarVariant("Mustang", "Ford", "Esportivo", 2021, BigDecimal.valueOf(300));
        CarVariant car4 = new CarVariant("Corolla", "Toyota", "Sedan", 2020, BigDecimal.valueOf(180));
        CarVariant car5 = new CarVariant("Cherokee", "Jeep", "SUV", 2017, BigDecimal.valueOf(260));

        carVariantRepository.saveAll(List.of(carModel, car2, car3, car4, car5));
        Car car = new Car("KHZ1T89", true);
        car.setCarVariant(carModel);

        carRepository.save(car);

        var token = authorizationServerService.createAccessToken(user.getUsername(), adminPassword);

        System.out.println(token);
    }
}
