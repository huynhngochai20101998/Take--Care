package com.tamaumi.takecare.security;

import com.tamaumi.takecare.entity.CustomUserDetails;
import io.jsonwebtoken.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {

    private final static Logger LOGGER = LogManager.getLogger(JwtTokenProvider.class);

    private final String JWT_SECRET = "tamaumi";

    private final long JWT_EXPIRATION = 604800000L;

    public String generateJWT(CustomUserDetails userDetails) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + JWT_EXPIRATION);

        return Jwts.builder()
                .setSubject(Long.toString(userDetails.getUser().getUserId()))
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
                .compact();
    }

    public Long getUserIdFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(JWT_SECRET)
                .parseClaimsJws(token)
                .getBody();

        return Long.parseLong(claims.getSubject());
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(authToken);
            return true;
        } catch (MalformedJwtException exception) {
            LOGGER.error("Invalid JWT token");
        } catch (ExpiredJwtException exception) {
            LOGGER.error("Expired JWT token");
        } catch (UnsupportedJwtException exception) {
            LOGGER.error("Unsupported JWT token");
        } catch (IllegalArgumentException exception) {
            LOGGER.error("JWT claims string is empty.");
        }

        return false;
    }
}
