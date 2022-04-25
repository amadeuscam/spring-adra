package com.amadeuscam.adratorrejon.security;

import com.amadeuscam.adratorrejon.exceptions.AdraAppException;
import com.amadeuscam.adratorrejon.models.UsuarioPrincipal;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${app.jwt-secret}")
    private String jwtSecret;

    @Value("${app.jwt-expiration-milliseconds}")
    private int jwtExpirationIOnMs;

    private final static Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

    public String generateToken(Authentication authentication) {
        UsuarioPrincipal usuarioPrincipal = (UsuarioPrincipal) authentication.getPrincipal();
        Date actualDate = new Date();
        Date expireDate = new Date(actualDate.getTime() + jwtExpirationIOnMs);

        return Jwts.builder().
                setSubject(usuarioPrincipal.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String getUsernameFromJwt(String token) {
        Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (SignatureException ex) {
            logger.error("Jwt signature no valid!");
            throw new AdraAppException(HttpStatus.BAD_REQUEST, "Jwt signature no valid!");

        } catch (MalformedJwtException ex) {
            logger.error("Jwt  no valid!");
            throw new AdraAppException(HttpStatus.BAD_REQUEST, "Jwt  no valid!");

        } catch (ExpiredJwtException ex) {
            logger.error("Token Jwt  expired!");
            throw new AdraAppException(HttpStatus.BAD_REQUEST, "Token Jwt  expired!");

        } catch (UnsupportedJwtException ex) {
            logger.error("Token Jwt  no compatible!");
            throw new AdraAppException(HttpStatus.BAD_REQUEST, "Token Jwt  no compatible!");

        } catch (IllegalArgumentException ex) {
            logger.error("La cadena claims Jwt esta vacia!");
            throw new AdraAppException(HttpStatus.BAD_REQUEST, "La cadena claims Jwt esta vacia!");

        }

    }


}
