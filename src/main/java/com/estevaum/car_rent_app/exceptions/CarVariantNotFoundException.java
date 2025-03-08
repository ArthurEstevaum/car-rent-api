package com.estevaum.car_rent_app.exceptions;

public class CarVariantNotFoundException extends RuntimeException {
    public CarVariantNotFoundException() {
        super("Modelo de carro não encontrado.");
    }
}
