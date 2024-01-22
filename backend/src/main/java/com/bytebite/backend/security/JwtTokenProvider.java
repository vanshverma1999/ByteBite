package com.bytebite.backend.security;

import com.bytebite.backend.exception.UserException;
import com.bytebite.backend.model.User;
import com.bytebite.backend.repository.UserRepository;
import com.bytebite.backend.response.AuthResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenProvider {
    @Value("${jwt.secret}")
    private String SECRET_KEY;

    @Value("${jwt.expiration}")
    private String EXPIRATION_IN_MS;

    @Autowired
    private UserRepository userRepository;

    public AuthResponse generateToken(UserPrincipal userPrincipal) {
        Map<String, Object> claims = new HashMap<>();
        Date now = new Date();
//        Date expiryDate = nonew Date(now.getTime() + EXPIRATION_IN_MS);
        claims.put("email", userPrincipal.getEmail());
        claims.put("role", userPrincipal.getAuthorities());
        if (userPrincipal.getAuthorities().contains(new SimpleGrantedAuthority("CUSTOMER"))) {
            userRepository.findById(userPrincipal.getId())
                    .ifPresent(
                            user -> {
                                claims.put("fullName", user.getFullName());
                            }
                    );
        }
        return createToken(claims, userPrincipal);
    }

    private AuthResponse createToken(Map<String, Object> claims, UserPrincipal userPrincipal) {
        String jwt =
                Jwts.builder()
                        .setSubject(Long.toString(userPrincipal.getId()))
                        .addClaims(claims)
                        .setIssuedAt(new Date())
//                        .setExpiration(expiryDate)
                        .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                        .compact();
        return AuthResponse.builder()
                .accessToken(jwt)
//                .expiration(Long.toString(expiryDate.toInstant().toEpochMilli()))
                .build();
    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public AuthResponse generateTokenFromAuthentication(Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        return generateToken(userPrincipal);
    }

    public AuthResponse generateTokenFromUserId(Long id) {

        User user =
                userRepository
                        .findById(id)
                        .orElseThrow(() -> new UserException("User not found for Id " + id));
        UserPrincipal userPrincipal = UserPrincipal.create(user);
        return generateToken(userPrincipal);
    }

    public Long getUserIdFromJWT(String token) {
        Claims claims =
                Jwts.parserBuilder()
                        .setSigningKey(getSigningKey())
                        .build()
                        .parseClaimsJws(token)
                        .getBody();

        return Long.parseLong(claims.getSubject());
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(authToken);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}
