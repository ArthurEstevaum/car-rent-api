package com.estevaum.car_rent_app.services;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;

@Service
public class ContractService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private RentingContractRepository contractRepository;

    private final Function<RentingContract, ContractInfoDTO> castContractToInfo =  contract -> {
        return new ContractInfoDTO(contract.getId(), contract.getStartDate(), contract.getEndDate(),
                contract.getContractTotalPrice(), contract.isCurrentContract() , new CarContractInfoDTO(contract.getCar().getLicensePlate(),
                contract.getCar().getId(), contract.getCar().getModel().getManufacturer(),
                contract.getCar().getModel().getName(), contract.getCar().getModel().getCategory(),
                contract.getCar().getModel().getModelYear()), new ContractUserInfoDTO(contract.getUser().getId(),
                contract.getUser().getUsername(), contract.getUser().getEmail(), contract.getUser().getPhoneNumber(),
                contract.getUser().getUserType()));
    };

    public List<ContractInfoDTO> getAllContracts() {
        List<RentingContract> contracts = contractRepository.findAll();

        return contracts.stream().map(castContractToInfo).toList();
    }

    public List<ContractInfoDTO> getUserContracts(String username) {
        if(!userRepository.existsByUsername(username)) {
            throw new UserNotFoundException();
        }

        List<RentingContract> userContracts = contractRepository.findByUserUsername(username);

        return userContracts.stream().map(castContractToInfo).toList();
    }

    public List<ContractInfoDTO> getCarContracts(Long id) {
        if(!carRepository.existsById(id)) {
            throw new CarNotFoundException("Carro não encontrado");
        }

        List<RentingContract> carContracts = contractRepository.findByCarId(id);

        return carContracts.stream().map(castContractToInfo).toList();
    }

    public RentingContract createRentingContract(CreateContractDTO requestData) {
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
        return contractRepository.save(newContract);
    }
}
