package pl.janksiegowy.backend.entity;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.janksiegowy.backend.entity.dto.ContactDto;
import pl.janksiegowy.backend.entity.dto.EntitiesDto;
import pl.janksiegowy.backend.entity.dto.EntityDto;
import pl.janksiegowy.backend.subdomain.DomainController;

import java.util.Optional;

@DomainController
@RequestMapping( "/v2/entities")
@AllArgsConstructor
public class EntityController {

    private final EntityQueryRepository query;
    private final EntityService service;
    private final ObjectMapper objectMapper;

    @GetMapping( "/")
    ResponseEntity<EntitiesDto> listContacts() {
    return ResponseEntity.ok( EntitiesDto.create()
                .contacts( query.findByType( ContactDto.class, EntityType.C)));
    }

    @PostMapping
    public ResponseEntity<EntityResponse> createEntity( @Valid @RequestBody EntityDto entityDto) {

        try {
            System.err.println( "Otrzymany JSON: " + objectMapper.writeValueAsString( entityDto));
        } catch (Exception e) {
            System.err.println( "Nie można zserializować: " + entityDto);
        }

        return Optional.ofNullable( service.create( entityDto))
                .map( response-> ResponseEntity.status( HttpStatus.CREATED)
                        .body( new EntityResponse( response.getEntityId())))
                .orElseGet(()-> ResponseEntity.status( HttpStatus.BAD_REQUEST).build());
    }

    @GetMapping
    public ResponseEntity<EntityDto> getEntity(
            @RequestParam String taxNumber,
            @RequestParam( defaultValue= "PL") Country country,
            @RequestParam EntityType type) {
        Optional<EntityDto> entity = query.findByCountryAndTypeAndTaxNumber( country, type, taxNumber);
        return entity.map( ResponseEntity::ok)
                .orElse( ResponseEntity.notFound().build());
    }

}
