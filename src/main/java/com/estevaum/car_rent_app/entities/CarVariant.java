package com.estevaum.car_rent_app.entities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Table(name = "tb_car_variants")
@Entity
public class CarVariant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    private String manufacturer;

    private String category;

    private Integer model_year;

    @Column(nullable = false)
    private BigDecimal rentPrice;

    @OneToMany(mappedBy = "model")
    private Set<Car> cars;

    public CarVariant(String name, String manufacturer, String category, Integer model_year, BigDecimal rentPrice) {
        this.name = name;
        this.manufacturer = manufacturer;
        this.category = category;
        this.model_year = model_year;
        this.rentPrice = rentPrice;
    }
}
