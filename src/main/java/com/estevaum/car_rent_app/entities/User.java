package com.estevaum.car_rent_app.entities;

import com.estevaum.car_rent_app.enums.UserTypes;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Table(name = "tb_users")
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Setter
    @Column(nullable = false, unique = true)
    private String email;

    @Setter
    @Column(nullable = false, unique = true)
    private String phoneNumber;

    private UserTypes userType;

    @OneToMany(mappedBy = "user")
    private Set<RentingContract> contracts;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_permissions", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "permission_id"))
    private Set<Permission> permissions;

    public User(String username, String encryptedPassword, String email, String phoneNumber, UserTypes userType) {
        this.username = username;
        this.password = encryptedPassword;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.userType = userType;
    }

    public void addPermission(Permission permission) {
        if(this.permissions == null) {
            this.permissions = new HashSet<>();
        }
        this.permissions.add(permission);
    }

    public Boolean hasActiveContracts() {
        Set<RentingContract> activeContracts = contracts.stream().filter(RentingContract::isCurrentContract).collect(Collectors.toSet());

        return !activeContracts.isEmpty();
    }
}
