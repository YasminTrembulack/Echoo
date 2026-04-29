package com.trycatchus.echoo.service;

import java.time.Instant;

import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.Verification;

import com.trycatchus.echoo.config.JwtProperties;
import com.trycatchus.echoo.dto.payload.auth.LoginPayload;
import com.trycatchus.echoo.dto.responses.auth.AuthResponse;
import com.trycatchus.echoo.dto.responses.auth.UserDecodedResponse;
import com.trycatchus.echoo.exception.JwtCreationException;
import com.trycatchus.echoo.exception.PasswordMismatchException;
import com.trycatchus.echoo.exception.EmptyTokenException;
import com.trycatchus.echoo.exception.EntityNotFoundException;
import com.trycatchus.echoo.exception.InvalidTokenException;
import com.trycatchus.echoo.interfaces.AuthService;
import com.trycatchus.echoo.interfaces.PasswordService;
import com.trycatchus.echoo.model.User;
import com.trycatchus.echoo.repository.UserRepository;

@Service
public class Auth0JwtService implements AuthService {

    private static final String BEARER_PREFIX = "Bearer ";

    private final JwtProperties jwtProperties;
    private final UserRepository userRepo;
    private final PasswordService passwordService;

    public Auth0JwtService(
        UserRepository userRepo, 
        PasswordService passwordService, 
        JwtProperties jwtProperties
    ) {
        this.userRepo = userRepo;
        this.passwordService = passwordService;
        this.jwtProperties = jwtProperties;
    }

    @Override
    public AuthResponse login(LoginPayload payload) {
        User user = userRepo.findByEmail(payload.email())
            .orElseThrow(() -> new EntityNotFoundException(User.class));

        Boolean passwordsMatch = passwordService.matchPasswords(
                payload.password(),
                user.getPasswordHash());

        if (!passwordsMatch)
            throw new PasswordMismatchException();

        String token = generateAccessToken(user);

        return new AuthResponse(token, user.getId());
    }

    @Override
    public UserDecodedResponse verifyToken(String auth) {
        DecodedJWT decodedToken = decodeToken(auth);

        return new UserDecodedResponse(
            decodedToken.getClaim("userId").asString(),
            decodedToken.getClaim("firstName").asString(),
            decodedToken.getClaim("username").asString(),
            decodedToken.getClaim("role").asString()
        );
    }

    @Override
    public DecodedJWT decodeToken(String auth) {
        String token = extractBearerToken(auth);

        if (token == null || token.isEmpty())
            throw new EmptyTokenException();

        Algorithm algorithm = Algorithm.HMAC256(jwtProperties.getSecret());
        Verification verification = JWT.require(algorithm).withIssuer("echoo-api");

        try {
            JWTVerifier verifier = verification.build();
            return verifier.verify(token);
            
        } catch (JWTVerificationException ex) {
            throw new InvalidTokenException();
        }
    }

    private String extractBearerToken(String auth) {
        if (auth == null) return null;

        String token = auth.trim();

        if (token.regionMatches(true, 0, BEARER_PREFIX, 0, BEARER_PREFIX.length()))
            return token.substring(BEARER_PREFIX.length()).trim();

        return token;
    }

    private String generateAccessToken(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(jwtProperties.getSecret());

            return JWT.create()
                    .withIssuer("echoo-api")
                    .withClaim("username", user.getUsername())
                    .withClaim("firstName", user.getFirstName())
                    .withClaim("userId", user.getId().toString())
                    .withClaim("role", user.getUserRole().name())
                    .withExpiresAt(Instant.now().plusSeconds(jwtProperties.getExpirationSeconds()))
                    .sign(algorithm);
        } catch (JWTCreationException ex) {
            throw new JwtCreationException();
        }
    }

}