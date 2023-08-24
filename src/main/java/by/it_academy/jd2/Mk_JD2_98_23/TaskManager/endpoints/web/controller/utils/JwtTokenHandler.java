package by.it_academy.jd2.Mk_JD2_98_23.TaskManager.endpoints.web.controller.utils;

import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.config.property.JWTProperty;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.dto.UserDetailsImpl;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.enums.EUserRole;
import io.jsonwebtoken.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Component
public class JwtTokenHandler {

    private static final String UUID_FIELD_NAME = "uuid";

    private static final String FIO_FIELD_NAME = "fio";

    private static final String ROLE_FIELD_NAME = "role";

    private final JWTProperty property;

    public JwtTokenHandler(JWTProperty property) {
        this.property = property;
    }

    public String generateAccessToken(UserDetailsImpl userDetails) {
        return generateAccessToken(
                userDetails.getUuid(),
                userDetails.getUsername(),
                userDetails.getFio(),
                userDetails.getRole());
    }

    public String generateAccessToken(UUID uuid, String mail, String fio, EUserRole role) {
        try {
            String compact = Jwts.builder()
                    .setSubject(mail)
                    .claim(UUID_FIELD_NAME, uuid)
                    .claim(FIO_FIELD_NAME, fio)
                    .claim(ROLE_FIELD_NAME, role)
                    .setIssuer(property.getIssuer())
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(7))) // 1 week
                    .signWith(SignatureAlgorithm.HS512, property.getSecret())
                    .compact();
            return compact;
        } catch (Exception ex){
            throw new RuntimeException(ex.getCause());
        }
    }

    public String getMail(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(property.getSecret())
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public String getUuid(String token){
        Claims claims = Jwts.parser()
                .setSigningKey(property.getSecret())
                .parseClaimsJws(token)
                .getBody();
        return claims.get(UUID_FIELD_NAME, String.class);
    }

    public String getFio(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(property.getSecret())
                .parseClaimsJws(token)
                .getBody();

        return claims.get(FIO_FIELD_NAME, String.class);
    }

    public String getRole(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(property.getSecret())
                .parseClaimsJws(token)
                .getBody();

        return claims.get(ROLE_FIELD_NAME, String.class);
    }


    public Date getExpirationDate(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(property.getSecret())
                .parseClaimsJws(token)
                .getBody();

        return claims.getExpiration();
    }

    public boolean validate(String token) {
        try {
            Jwts.parser().setSigningKey(property.getSecret()).parseClaimsJws(token);
            return true;
        } catch (SignatureException ex) {
            //logger.error("Invalid JWT signature - {}", ex.getMessage());
        } catch (MalformedJwtException ex) {
            //logger.error("Invalid JWT token - {}", ex.getMessage());
        } catch (ExpiredJwtException ex) {
            //logger.error("Expired JWT token - {}", ex.getMessage());
        } catch (UnsupportedJwtException ex) {
            //logger.error("Unsupported JWT token - {}", ex.getMessage());
        } catch (IllegalArgumentException ex) {
            //logger.error("JWT claims string is empty - {}", ex.getMessage());
        }
        return false;
    }
}
