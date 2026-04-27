package com.trycatchus.echoo.service;

import java.time.Instant;

import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.Verification;
import com.trycatchus.echoo.config.JwtProperties;
import com.trycatchus.echoo.dto.HttpEntity;
import com.trycatchus.echoo.dto.payload.login.LoginPayload;
import com.trycatchus.echoo.dto.responses.login.AuthResponse;
import com.trycatchus.echoo.exception.ApplicationException;
import com.trycatchus.echoo.interfaces.AuthService;
import com.trycatchus.echoo.interfaces.PasswordService;
import com.trycatchus.echoo.repository.UserRepository;

@Service
public class Auth0JwtService implements AuthService {

    private final JwtProperties jwtProperties;
    private final UserRepository userRepo;
    private final PasswordService passwordService;

    public Auth0JwtService(UserRepository userRepo, PasswordService passwordService, JwtProperties jwtProperties) {
        this.userRepo = userRepo;
        this.passwordService = passwordService;
        this.jwtProperties = jwtProperties;
    }

    @Override
    public HttpEntity<AuthResponse> loginAsync(LoginPayload payload) {

        var user = userRepo.findByEmail(payload.email())
                .orElseThrow(() -> new ApplicationException(404, "User not found."));

        var passwordsMatch = passwordService.matchPasswords(
                payload.password(),
                user.getPasswordHash());

        if (!passwordsMatch)
            throw new ApplicationException(400, "Passwords do not match.");

        String token;
        try {
            Algorithm algorithm = Algorithm.HMAC256(jwtProperties.getSecret());

            token = JWT.create()
                    .withIssuer("echoo-api")
                    .withClaim("userId", user.getId().toString())
                    .withClaim("role", user.getUserRole().name())
                    .withExpiresAt(Instant.now().plusSeconds(jwtProperties.getExpirationSeconds()))
                    .sign(algorithm);
        } catch (JWTCreationException ex) {
            throw new ApplicationException(500, "Claims couldn't be converted.");
        }

        var response = new AuthResponse("Successful login.", token, user.getId());

        return new HttpEntity<AuthResponse>(
                HttpStatusCode.valueOf(200),
                response);
    }

    @Override
    public DecodedJWT decodeTokenAsync(String auth) {
        if (auth == null || !auth.startsWith("Bearer ")) {
            throw new ApplicationException(403, "Invalid token format.");
        }

        var token = auth.substring(7);

        Algorithm algorithm = Algorithm.HMAC256(jwtProperties.getSecret());
        Verification verification = JWT.require(algorithm)
                .withIssuer("echoo-api");

        try {
            JWTVerifier verifier = verification.build();
            return verifier.verify(token);
        } catch (JWTVerificationException ex) {
            throw new ApplicationException(403, "Invalid token.");
        }
    }
}