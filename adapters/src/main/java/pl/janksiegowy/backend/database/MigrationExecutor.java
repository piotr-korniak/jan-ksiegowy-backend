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

    public String executeMigration( MigrationDto step) {
        var response= "Step already executed: "+ step.getUrl();

        if( step.isAlways()|| !updateRepository.existsByStepUrl( step.getUrl())) {
            try {
                HttpMethod method= HttpMethod.valueOf( step.getMethod().toUpperCase());
                String baseUrl= ServletUriComponentsBuilder.fromCurrentRequestUri()
                        .replacePath( null) // Usuwa całą ścieżkę (URI)
                        .toUriString();
                HttpEntity<Map<String, String>> request= new HttpEntity<>( new HashMap<>());

                response= restTemplate.exchange(baseUrl+ step.getUrl(), method, request, String.class).getBody();

                // Zapisz w historii tylko kroki jednorazowe
                if( !step.isAlways()) {
                    updateRepository.save( new Update()
                            .setStepUrl( step.getUrl())
                            .setExecutedAt( LocalDateTime.now())
                            .setResult( response));
                }
            } catch( Exception e) {
                 response= "Failed to execute step: "+ step.getUrl();
            }
        }
        return response;
    }
}
