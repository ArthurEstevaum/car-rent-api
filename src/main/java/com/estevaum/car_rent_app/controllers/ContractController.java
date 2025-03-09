package com.estevaum.car_rent_app.controllers;

import com.estevaum.car_rent_app.DTO.CreateContractDTO;
import com.estevaum.car_rent_app.entities.Car;
import com.estevaum.car_rent_app.entities.RentingContract;
import com.estevaum.car_rent_app.entities.User;
import com.estevaum.car_rent_app.enums.UserTypes;
import com.estevaum.car_rent_app.exceptions.CarNotFoundException;
import com.estevaum.car_rent_app.exceptions.NumberOfContractsExceeded;
import com.estevaum.car_rent_app.exceptions.UserNotFoundException;
import com.estevaum.car_rent_app.repositories.CarRepository;
import com.estevaum.car_rent_app.repositories.RentingContractRepository;
import com.estevaum.car_rent_app.repositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/contracts")
@RestController
public class ContractController {

    @Autowired
    private RentingContractRepository contractRepository;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/")
    public ResponseEntity<String> createRentingContract(@RequestBody @Valid CreateContractDTO requestData) {
        User user = userRepository.findByUsername(requestData.username()).orElseThrow(() -> new UserNotFoundException("Usuário não encontrado."));

        if(user.hasActiveContracts() && user.getUserType() == UserTypes.personal) {
            throw new NumberOfContractsExceeded("Uma pessoa física pode ter apenas um contrato ativo");
        }

        Car car = carRepository.findById(requestData.carId()).orElseThrow(() -> new CarNotFoundException("Carro não encontrado."));

        RentingContract newContract = new RentingContract(car, requestData.endDate(), requestData.startDate(), user);

        return ResponseEntity.ok("Contrato efetuado com sucesso.");
    }
}
