package pl.janksiegowy.backend.database;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.janksiegowy.backend.shared.update.Update;
import pl.janksiegowy.backend.shared.update.UpdateRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@AllArgsConstructor
public class MigrationExecutor {

    private final RestTemplate restTemplate= new RestTemplate();
    private final UpdateRepository updateRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public ResponseEntity executeMigration( MigrationDto step) {

        ResponseEntity<String> response;

        if( step.isAlways()|| !updateRepository.existsByStepUrl( step.getUrl())) {
            try {
                HttpMethod method= HttpMethod.valueOf( step.getMethod().toUpperCase());
                String baseUrl= ServletUriComponentsBuilder.fromCurrentRequestUri()
                        .replacePath( null) // Usuwa całą ścieżkę (URI)
                        .toUriString();
                HttpEntity<Map<String, String>> request= new HttpEntity<>( new HashMap<>());

                response= restTemplate.exchange(baseUrl+ step.getUrl(),
                        method,
                        request,
                        String.class
                );
                // Zapisz w historii tylko kroki jednorazowe
                if( !step.isAlways()) {
                    updateRepository.save( new Update()
                            .setStepUrl( step.getUrl())
                            .setExecutedAt( LocalDateTime.now()));
                }

            } catch( Exception e) {
                 response= ResponseEntity.ok("Failed to execute step: "+ step.getUrl());
            }
        }else{
            response= ResponseEntity.ok( "Step already executed: "+ step.getUrl());
        }
        return response;
    }
}
