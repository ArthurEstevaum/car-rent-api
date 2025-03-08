package com.estevaum.car_rent_app.controllers;

import com.estevaum.car_rent_app.DTO.CarRegistrationDTO;
import com.estevaum.car_rent_app.DTO.CarShortInfoDTO;
import com.estevaum.car_rent_app.DTO.CarUpdateDTO;
import com.estevaum.car_rent_app.entities.Car;
import com.estevaum.car_rent_app.entities.CarVariant;
import com.estevaum.car_rent_app.exceptions.CarNotFoundException;
import com.estevaum.car_rent_app.exceptions.CarVariantNotFoundException;
import com.estevaum.car_rent_app.repositories.CarRepository;
import com.estevaum.car_rent_app.repositories.CarVariantRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@RequestMapping("/cars")
@RestController
public class CarController {

    @Autowired
    private CarRepository carRepository;

    @Autowired
    CarVariantRepository carVariantRepository;

    private final Function<Car, CarShortInfoDTO> carToShortInfo = car -> {
        CarVariant carModel = car.getModel();

        return new CarShortInfoDTO(car.getId(), car.getAvailable(), carModel.getName(),
                carModel.getManufacturer(), carModel.getCategory(), carModel.getModelYear(),
                carModel.getRentPrice());
    };

    @GetMapping("/")
    public ResponseEntity<List<CarShortInfoDTO>> listCars(@RequestParam(name = "available", required = false) Boolean queryOnlyAvailable) {
        if(queryOnlyAvailable == null) {
            return ResponseEntity.ok(carRepository.findAll().stream().map(carToShortInfo).toList());
        }

        return ResponseEntity.ok(carRepository.findByAvailableTrue().stream().map(carToShortInfo).toList());
    }

    @GetMapping("/models/{modelName}")
    public ResponseEntity<List<CarShortInfoDTO>> searchCarsByModelName(@PathVariable String modelName, @RequestParam(name = "available", required = false) Boolean queryOnlyAvailable) {
        if(queryOnlyAvailable == null) {
            return ResponseEntity.ok(carRepository.findByModelName(modelName).stream().map(carToShortInfo).toList());
        }

        return ResponseEntity.ok(carRepository.findByModelNameAndAvailableTrue(modelName).stream().map(carToShortInfo).toList());
    }

    @GetMapping("/manufacturers/{manufacturer}")
    public ResponseEntity<List<CarShortInfoDTO>> searchCarsByManufacturer(@PathVariable String manufacturer, @RequestParam(name = "available", required = false) Boolean queryOnlyAvailable) {
        if(queryOnlyAvailable == null) {
            return ResponseEntity.ok(carRepository.findByModelManufacturer(manufacturer).stream().map(carToShortInfo).toList());
        }

        return ResponseEntity.ok(carRepository.findByModelManufacturerAndAvailableTrue(manufacturer).stream().map(carToShortInfo).toList());
    }

    @GetMapping("/categories/{category}")
    public ResponseEntity<List<CarShortInfoDTO>> searchCarsByCategory(@PathVariable String category, @RequestParam(name = "available", required = false) Boolean queryOnlyAvailable) {
        if(queryOnlyAvailable == null) {
            return ResponseEntity.ok(carRepository.findByModelCategory(category).stream().map(carToShortInfo).toList());
        }

        return ResponseEntity.ok(carRepository.findByModelCategoryAndAvailableTrue(category).stream().map(carToShortInfo).toList());
    }

    @GetMapping("/model-years/{modelYear}")
    public ResponseEntity<List<CarShortInfoDTO>> searchCarsByModelYear(@PathVariable Integer modelYear, @RequestParam(name = "available", required = false) Boolean queryOnlyAvailable) {
        if(queryOnlyAvailable == null) {
            return ResponseEntity.ok(carRepository.findByModelModelYear(modelYear).stream().map(carToShortInfo).toList());
        }

        return ResponseEntity.ok(carRepository.findByModelModelYearAndAvailableTrue(modelYear).stream().map(carToShortInfo).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Car> searchCar(@PathVariable Long id) {
        Car car = carRepository.findById(id).orElseThrow(() -> new CarNotFoundException("Nenhum carro encontrado com este identificador."));

        return ResponseEntity.ok(car);
    }

    @PostMapping("/")
    public ResponseEntity<Car> createCar(@RequestParam(name = "legacy-plate", required = false) Boolean hasLegacyLicensePlate, @RequestBody @Valid CarRegistrationDTO requestData) {

        CarVariant carModel = carVariantRepository.findByName(requestData.modelName()).orElseThrow(CarVariantNotFoundException::new);
        Car newCar = hasLegacyLicensePlate != null?
                new Car(requestData.licensePlate(), requestData.available(), hasLegacyLicensePlate)
                : new Car(requestData.licensePlate(), requestData.available());

        newCar.setCarVariant(carModel);

        carRepository.save(newCar);

        return ResponseEntity.ok(newCar);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateCar(@PathVariable Long id, @RequestBody CarUpdateDTO requestData) {
        Car car = carRepository.findById(id).orElseThrow(() -> new CarNotFoundException("Nenhum carro encontrado com este identificador."));

        Optional.ofNullable(requestData.newLicensePlate()).ifPresent(car::setLicensePlate);
        Optional.ofNullable(requestData.available()).ifPresent(car::setAvailable);

        return ResponseEntity.ok("Carro atualizado.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCar(@PathVariable Long id) {
        carRepository.deleteById(id);

        return ResponseEntity.ok("Carro removido.");
    }
}
