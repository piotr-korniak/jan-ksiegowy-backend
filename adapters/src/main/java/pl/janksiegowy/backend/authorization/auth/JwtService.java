package pl.janksiegowy.backend.authorization.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Service
@AllArgsConstructor
public class JwtService {

    private final JwtProperties jwtProperties; // secret, expiry z application.yml

    // ── generowanie ───────────────────────────────────────────

    public String generateBaseToken(UUID userId) {
        return Jwts.builder()
                .subject(userId.toString())
                .claim("tokenType", "BASE")
                .issuedAt(new Date())
                .expiration(expiresIn(jwtProperties.getBaseExpiry()))
                .signWith(signingKey())
                .compact();
    }

    public String generateAccessToken( UUID userId, String subdomain, String schemaName) {
        System.err.println( "User ID: " + userId.toString());
        System.err.println( "Subdomain: " + subdomain);
        System.err.println( "Schema Name: " + schemaName);
        return Jwts.builder()
                .subject( userId.toString())
                .claim( "token", "ACCESS")
                .claim( "tenant", subdomain)
                .claim( "schema",schemaName)
                .issuedAt( new Date())
                .expiration( expiresIn( jwtProperties.getAccessExpiry()))
                .signWith( signingKey())
                .compact();
    }

    public String generateRefreshToken(UUID userId) {
        return Jwts.builder()
                .subject(userId.toString())
                .claim("tokenType", "REFRESH")
                .issuedAt(new Date())
                .expiration(expiresIn(jwtProperties.getRefreshExpiry()))
                .signWith(signingKey())
                .compact();
    }

    // ── odczyt ────────────────────────────────────────────────

    public UUID extractUserId(String token) {
        return UUID.fromString(parseClaims(token).getSubject());
    }

    public String extractTokenType(String token) {
        return parseClaims(token).get("tokenType", String.class);
    }

    public String extractSubdomain(String token) {
        return parseClaims(token).get("subdomain", String.class);
    }

    public String extractSchemaName(String token) {
        return parseClaims(token).get("schemaName", String.class);
    }

    public String extractRole(String token) {
        return parseClaims(token).get("role", String.class);
    }

    public boolean isTokenValid(String token) {
        try {
            parseClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    // ── pomocnicze ────────────────────────────────────────────

    Claims parseClaims(String token) {
        return Jwts.parser()
                .verifyWith(signingKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private SecretKey signingKey() {
        return Keys.hmacShaKeyFor(
                Decoders.BASE64.decode( jwtProperties.getSecret()));
    }

    private Date expiresIn( Duration duration) {
        return Date.from(Instant.now().plus( duration));
    }
}
