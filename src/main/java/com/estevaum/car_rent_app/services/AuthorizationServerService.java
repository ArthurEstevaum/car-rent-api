package com.estevaum.car_rent_app.services;

import com.estevaum.car_rent_app.entities.Permission;
import com.estevaum.car_rent_app.entities.User;
import com.estevaum.car_rent_app.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

@Service
public class AuthorizationServerService {

    @Autowired
    private JwtEncoder jwtEncoder;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
    private String appIssuer;

    public String createAccessToken(String username, String password) throws Exception {

        User user = userRepository.findByUsername(username).orElseThrow((Supplier<BadCredentialsException>) () -> new BadCredentialsException("Credenciais inválidas."));
        boolean correctPassword = passwordEncoder.matches(password, user.getPassword());

        if(!correctPassword) {
            throw new BadCredentialsException("Credenciais inválidas.");
        }

        List<String> permissions = user.getPermissions().stream().map(Permission::getName).toList();

        var claims = JwtClaimsSet.builder()
                .issuer(appIssuer)
                .subject(username)
                .expiresAt(Instant.now().plusSeconds(300))
                .issuedAt(Instant.now())
                .claim("scope", permissions)
                .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }
}
