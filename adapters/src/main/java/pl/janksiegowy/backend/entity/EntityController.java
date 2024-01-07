package pl.janksiegowy.backend.entity;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.janksiegowy.backend.entity.dto.ContactDto;
import pl.janksiegowy.backend.entity.dto.EntitiesDto;
import pl.janksiegowy.backend.subdomain.TenantController;

@TenantController
@RequestMapping( "/v2")
@AllArgsConstructor
public class EntityController {

    private final EntityQueryRepository query;

    @GetMapping( "/contacts")
    ResponseEntity<EntitiesDto> listContacts() {


        return ResponseEntity.ok( EntitiesDto.create()
                .contacts( query.findByType( ContactDto.class, EntityType.C)));
    }
}
