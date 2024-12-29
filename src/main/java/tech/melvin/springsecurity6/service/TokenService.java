package tech.melvin.springsecurity6.service;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import tech.melvin.springsecurity6.controller.dto.LoginRequest;
import tech.melvin.springsecurity6.controller.dto.LoginResponse;
import tech.melvin.springsecurity6.entity.Role;
import tech.melvin.springsecurity6.repository.UserRepository;

import java.time.Instant;
import java.util.stream.Collectors;

@Service
public class TokenService {
    private final JwtEncoder jwtEncoder;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public TokenService(JwtEncoder jwtEncoder, UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.jwtEncoder = jwtEncoder;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public LoginResponse login(LoginRequest loginRequest) {
        var user = userRepository.findByUsername(loginRequest.username());

        if (user.isEmpty() || !user.get().isLoginCorrect(loginRequest, passwordEncoder)) {
            throw new BadCredentialsException("User or password is invalid!");
        }

        var now = Instant.now();
        var expiresIn = 1000L;

        var scopes = user.get().getRoles()
             .stream()
             .map(Role::getName)
             .collect(Collectors.joining(" "));

        var claims = JwtClaimsSet.builder()
             .issuer("mybackend")
             .subject(user.get().getUserId().toString())
             .issuedAt(now)
             .expiresAt(now.plusSeconds(expiresIn))
             .claim("scope", scopes)
             .build();

        var jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

        return new LoginResponse(jwtValue, expiresIn);
    }
}
