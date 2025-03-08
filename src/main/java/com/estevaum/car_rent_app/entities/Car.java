package com.estevaum.car_rent_app.entities;

import com.estevaum.car_rent_app.exceptions.InvalidLicensePlateException;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@EqualsAndHashCode(of = "id")
@Table(name = "tb_cars")
@Entity
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String licensePlate;

    @Setter
    @Column(nullable = false)
    private Boolean available;

    @OneToOne()
    private RentingContract currentContract;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "car_variant_id")
    private CarVariant model;

    public Car(String licensePlate, Boolean available) {
        setLicensePlate(licensePlate);
        this.available = available;
    }

    public Car(String licensePlate, Boolean available, Boolean hasLegacyLicensePlate) {
        if(hasLegacyLicensePlate) {
            setLegacyLicensePlate(licensePlate);
            this.available = available;
            return;
        }

        setLicensePlate(licensePlate);
        this.available = available;
    }

    public void setLicensePlate(String licensePlate) {
        String regex = "^[A-Z]{3}\\d[A-Z]\\d{2}$";
        boolean validLicensePlate = licensePlate.matches(regex);

        if(!validLicensePlate) {
            throw new InvalidLicensePlateException();
        }

        this.licensePlate = licensePlate;
    }

    /**
     * Use this setter function when the car has a legacy license plate format (formato antigo)
     * @param licensePlate licensePlate to be set
     */
    public void setLegacyLicensePlate(String licensePlate) {
        String regex = "^[A-Z]{3}-\\d{4}$";
        boolean validLicensePlate = licensePlate.matches(regex);

        if(!validLicensePlate) {
            throw new InvalidLicensePlateException();
        }

        this.licensePlate = licensePlate;
    }

    public void setCarVariant(CarVariant carVariant) {
        model = carVariant;
    }
}
