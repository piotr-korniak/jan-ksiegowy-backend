package pl.janksiegowy.backend.organization;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.janksiegowy.backend.organization.command.RegisterTenantWithInitialCompanyCommand;

@RestController
@RequestMapping("/v2/organization")
@AllArgsConstructor
public class MainController {

    private final OrganizationFacade organizationFacade;

    @PostMapping
    public ResponseEntity<RegisterTenantResponse> registerTenantWithInitialCompany(
            @Valid @RequestBody RegisterTenantRequest request
    ){

        System.out.println( "Register tenant with initial company: " + request);

        organizationFacade.registerTenantWithInitialCompany(
                new RegisterTenantWithInitialCompanyCommand(
                        request.nip(),
                        request.email(),
                        request.password(),
                        request.email()
                                .substring(request.email().indexOf( "@") + 1)
                                .replaceFirst("\\.[^.]+$", "")
                ));

        return ResponseEntity.status( HttpStatus.CREATED).body( new RegisterTenantResponse( "Ok!"));
    }

}
