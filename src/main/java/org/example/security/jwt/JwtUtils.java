package org.example.security.jwt;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.example.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;
/**
 * Clase utilitaria para generar, validar y obtener información de los tokens JWT (JSON Web Tokens).
 */
@Component
@Slf4j
public class JwtUtils {
    @Value("${bezkoder.app.jwtSecret}")

    private String jwtSecret;

    @Value("${bezkoder.app.jwtExpirationMs}")

    private int jwtExpirationMs;
    /**
     * Genera un token JWT utilizando la información de autenticación proporcionada en el objeto Authentication.
     *
     * @param authentication El objeto Authentication que contiene la información de autenticación del usuario.
     * @return El token JWT generado.
     */
    public String generateJwtToken(Authentication authentication) {

        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject((userPrincipal.getUsername()))
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }
    /**
     * Extrae el nombre de usuario del token JWT proporcionado.
     *
     * @param token El token JWT del cual se desea extraer el nombre de usuario.
     * @return El nombre de usuario extraído del token JWT.
     */
    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }
    /**
     * Valida la firma y la integridad de un token JWT.
     *
     * @param authToken El token JWT que se va a validar.
     * @return true si el token JWT es válido, false si no lo es.
     */
    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            log.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }
}