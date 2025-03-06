package com.estevaum.car_rent_app.entities;

import com.estevaum.car_rent_app.enums.UserTypes;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

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

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String phoneNumber;

    private UserTypes userType;

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
}
