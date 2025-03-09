package com.estevaum.car_rent_app.entities;

import com.estevaum.car_rent_app.exceptions.InvalidDateException;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "tb_renting_contract")
public class RentingContract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @ManyToOne
    @JoinColumn(name = "car_id")
    private Car car;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public RentingContract(Car car, LocalDate endDate, LocalDate startDate, User user) {
        if (endDate.isBefore(startDate)) {
            throw new InvalidDateException("A data de término não pode ser anterior à data de início.");
        }

        this.car = car;
        this.endDate = endDate;
        this.startDate = startDate;
        this.user = user;
    }

    public Boolean isCurrentContract() {
        LocalDate now = LocalDate.now();

        return now.isBefore(endDate);
    }

    public BigDecimal getContractTotalPrice() {
        long contractDuration = ChronoUnit.DAYS.between(startDate, endDate);
        BigDecimal dailyRentPrice = car.getModel().getRentPrice();

        return dailyRentPrice.multiply(BigDecimal.valueOf(contractDuration));
    }
}
