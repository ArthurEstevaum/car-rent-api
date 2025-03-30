package com.estevaum.car_rent_app.controllers;

import com.estevaum.car_rent_app.DTO.Contracts.ContractInfoDTO;
import com.estevaum.car_rent_app.DTO.Contracts.CreateContractDTO;
import com.estevaum.car_rent_app.entities.RentingContract;
import com.estevaum.car_rent_app.services.ContractService;
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
    private ContractService contractService;

    @GetMapping("/")
    public ResponseEntity<List<ContractInfoDTO>> listAllContracts() {
        return ResponseEntity.ok(contractService.getAllContracts());
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<List<ContractInfoDTO>> listUserContracts(@PathVariable String username) {
        return ResponseEntity.ok(contractService.getUserContracts(username));
    };

    @GetMapping("/car/{id}")
    public ResponseEntity<List<ContractInfoDTO>> listCarContracts(@PathVariable Long id) {
        return ResponseEntity.ok(contractService.getCarContracts(id));
    }

    @PostMapping("/")
    public ResponseEntity<String> createRentingContract(@RequestBody @Valid CreateContractDTO requestData) {
        RentingContract contract = contractService.createRentingContract(requestData);

        return ResponseEntity.ok("Contrato de nº " + contract.getId() + " efetuado com sucesso.");
    }
}
