package com.NewsAggregator.NewsAggregator.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.lang.Collections;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;

import java.security.Key;
import java.util.Collection;
import java.util.Date;

public class JwtUtility {
    private static Key secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);


    public static String generateToken(String username, String role) {

        return Jwts.builder()
                .subject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
//Multiple roles       .claim("roles", Collections.of("ROLE" + role))
                .claim("roles", "ROLE" + role)
                .signWith(secretKey)
                .compact();
    }

    public static boolean validateToken(String authHeader) {
        try {
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return false;
            }
            String token = authHeader.substring(7); // Remove "Bearer " prefix
            Claims claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            return claims.getExpiration().after(new Date());

        } catch (Exception e) {
            // Log the exception if needed
            return false;
        }

    }


    public static Claims getClaimsFromToken(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return null;
        }
        String token = authHeader.substring(7); // Remove "Bearer " prefix
        return Jwts.parser()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
