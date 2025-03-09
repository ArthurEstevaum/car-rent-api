package com.estevaum.car_rent_app.controllers;

import com.estevaum.car_rent_app.DTO.Contracts.CarContractInfoDTO;
import com.estevaum.car_rent_app.DTO.Contracts.ContractInfoDTO;
import com.estevaum.car_rent_app.DTO.Contracts.ContractUserInfoDTO;
import com.estevaum.car_rent_app.DTO.Contracts.CreateContractDTO;
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

import java.awt.image.renderable.RenderableImage;
import java.util.List;

@RequestMapping("/contracts")
@RestController
public class ContractController {

    @Autowired
    private RentingContractRepository contractRepository;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/")
    public ResponseEntity<List<ContractInfoDTO>> listAllContracts() {
        List<RentingContract> contracts = contractRepository.findAll();

        List<ContractInfoDTO> contractResponse = contracts.stream().map(contract -> {
            return new ContractInfoDTO(contract.getId(), contract.getStartDate(), contract.getEndDate(),
                    contract.getContractTotalPrice(), contract.isCurrentContract() , new CarContractInfoDTO(contract.getCar().getLicensePlate(),
                    contract.getCar().getId(), contract.getCar().getModel().getManufacturer(),
                    contract.getCar().getModel().getName(), contract.getCar().getModel().getCategory(),
                    contract.getCar().getModel().getModelYear()), new ContractUserInfoDTO(contract.getUser().getId(),
                    contract.getUser().getUsername(), contract.getUser().getEmail(), contract.getUser().getPhoneNumber(),
                    contract.getUser().getUserType()));
        }).toList();

        return ResponseEntity.ok(contractResponse);
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<List<ContractInfoDTO>> listUserContracts(@PathVariable String username) {
        if(!userRepository.existsByUsername(username)) {
            throw new UserNotFoundException();
        }

        List<RentingContract> userContracts = contractRepository.findByUserUsername(username);

        List<ContractInfoDTO> contractResponse = userContracts.stream().map(contract -> {
            return new ContractInfoDTO(contract.getId(), contract.getStartDate(), contract.getEndDate(),
                    contract.getContractTotalPrice(), contract.isCurrentContract() , new CarContractInfoDTO(contract.getCar().getLicensePlate(),
                    contract.getCar().getId(), contract.getCar().getModel().getManufacturer(),
                    contract.getCar().getModel().getName(), contract.getCar().getModel().getCategory(),
                    contract.getCar().getModel().getModelYear()), new ContractUserInfoDTO(contract.getUser().getId(),
                    contract.getUser().getUsername(), contract.getUser().getEmail(), contract.getUser().getPhoneNumber(),
                    contract.getUser().getUserType()));
        }).toList();

        return ResponseEntity.ok(contractResponse);
    };

    @GetMapping()

    @PostMapping("/")
    public ResponseEntity<String> createRentingContract(@RequestBody @Valid CreateContractDTO requestData) {
        User user = userRepository.findByUsername(requestData.username()).orElseThrow(UserNotFoundException::new);

        if(user.hasActiveContracts() && user.getUserType() == UserTypes.personal) {
            throw new NumberOfContractsExceeded("Uma pessoa física pode ter apenas um contrato ativo.");
        }

        Car car = carRepository.findById(requestData.carId()).orElseThrow(() -> new CarNotFoundException("Carro não encontrado."));

        Boolean carHasActiveContracts = contractRepository.existsByCarId(car.getId());

        if(carHasActiveContracts) {
            throw new NumberOfContractsExceeded("Um carro pode ter apenas um contrato ativo.");
        }

        RentingContract newContract = new RentingContract(car, requestData.endDate(), requestData.startDate(), user);
        contractRepository.save(newContract);

        return ResponseEntity.ok("Contrato efetuado com sucesso.");
    }
}
