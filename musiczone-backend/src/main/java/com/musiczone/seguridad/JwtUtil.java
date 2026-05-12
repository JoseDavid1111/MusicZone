package com.musiczone.seguridad;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {

    private final String SECRET = "musiczoneClaveSecretaJWT2024!!XYZ";
    private final long EXPIRATION = 1000 * 60 * 60; // 1 hora

    private SecretKey getKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes());
    }

    public String generarToken(String nombreUsuario) {
        return Jwts.builder()
                .subject(nombreUsuario)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(getKey())
                .compact();
    }

    public String extraerUsuario(String token) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public boolean validarToken(String token, String nombreUsuario) {
        try {
            String usuarioEnToken = extraerUsuario(token);
            Date expiracion = Jwts.parser()
                    .verifyWith(getKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .getExpiration();
            return usuarioEnToken.equals(nombreUsuario) && !expiracion.before(new Date());
        } catch (Exception e) {
            return false;
        }
    }
}
