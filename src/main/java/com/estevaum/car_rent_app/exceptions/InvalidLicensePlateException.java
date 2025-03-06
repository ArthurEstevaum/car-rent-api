package com.estevaum.car_rent_app.exceptions;

public class InvalidLicensePlateException extends RuntimeException {
    public InvalidLicensePlateException() {
        super("O formato fornecido para a placa do carro é invalido.");
    }
}
