package mx.edu.utez.firstapp.security.jwt;

import io.jsonwebtoken.*;
import mx.edu.utez.firstapp.security.model.UserAuth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;


@Component
public class JwtProvider {
    public final static Logger LOGGER = LoggerFactory.getLogger(JwtProvider.class);
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    private int expiration;

    public String generateToken(Authentication authentication) {
        UserAuth userAuth = (UserAuth) authentication.getPrincipal(); // Obtenemos el usuario autenticado
        return Jwts.builder() // Creamos el token
                .setSubject(userAuth.getUsername()) // Establecemos el usuario
                .setIssuedAt(new Date()) // Establecemos la fecha de creación del token
                .setExpiration(new Date(new Date().getTime() + expiration * 1000L)) // Establecemos la fecha de expiración del token con la duración que se estableció en el archivo application.properties
                .signWith(SignatureAlgorithm.HS512, secret) // Firmamos el token con el algoritmo HS512 y la clave secreta
                .compact(); // Convertimos el token en una cadena y lo retornamos
    }

    public String getUsernameFromToken(String token) {
        return Jwts.parser() // Parseamos el token
                .setSigningKey(secret) // Establecemos la clave secreta
                .parseClaimsJws(token) // Parseamos el token
                .getBody() // Obtenemos el cuerpo del token
                .getSubject(); // Obtenemos el usuario
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser() // Parseamos el token
                    .setSigningKey(secret) // Establecemos la clave secreta
                    .parseClaimsJws(token); // Parseamos el token
            return true; // Si no se lanza una excepción, el token es válido
        } catch (MalformedJwtException e) {
            LOGGER.error("ERROR malformed token: " + e.getMessage());
        } catch (UnsupportedJwtException e){
            LOGGER.error("ERROR unsupported token: " + e.getMessage());
        } catch (ExpiredJwtException e) {
            LOGGER.error("ERROR expired token: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            LOGGER.error("ERROR illegal argument: " + e.getMessage());
        } catch (SignatureException e) {
            LOGGER.error("ERROR signature: " + e.getMessage());
        }
        return false; // Si se lanza una excepción, el token no es válido
    }

}
