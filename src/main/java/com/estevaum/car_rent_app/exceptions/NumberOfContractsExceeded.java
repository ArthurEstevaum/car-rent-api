package com.estevaum.car_rent_app.exceptions;

public class NumberOfContractsExceeded extends RuntimeException {
    public NumberOfContractsExceeded(String message) {
        super(message);
    }
}
