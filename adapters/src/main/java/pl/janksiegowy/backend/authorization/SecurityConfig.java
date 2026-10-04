package pl.janksiegowy.backend.authorization;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain( HttpSecurity http) throws Exception {
        http
                .csrf( AbstractHttpConfigurer::disable) // dla REST-owego API zwykle wyłączamy CSRF [web:195]
                .authorizeHttpRequests(auth -> auth
                        // ten endpoint ma być PUBLICZNY (bez JWT)
                        //.requestMatchers( HttpMethod.POST, "/v2/organization").permitAll()
                        // ewentualnie inne publiczne:
                        //.requestMatchers("/auth/**", "/public/**").permitAll()
                        // cała reszta musi mieć token
                        //.anyRequest().authenticated()
                        .anyRequest().permitAll()
                );
        // Tutaj masz jeszcze konfigurację filtrów, np. JwtAuthFilter

        return http.build();
    }
}
