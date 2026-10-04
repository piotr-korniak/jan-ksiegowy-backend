package pl.janksiegowy.backend.authorization.auth;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.time.Duration;

@ConfigurationProperties( prefix = "jwt")
@Component
@Getter
@Setter
public class JwtProperties {
    private String secret= "LitwoOjczyznoMojatysJestJakZdrowieAzSieZapsujesz1970";
    private Duration baseExpiry= Duration.ofMinutes( 5);
    private Duration accessExpiry= Duration.ofMinutes( 30);
    private Duration refreshExpiry= Duration.ofDays( 7);
}