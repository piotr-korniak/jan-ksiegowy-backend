package pl.janksiegowy.backend.organization.tenant;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.janksiegowy.backend.organization.command.CreateTenantCommand;
import pl.janksiegowy.backend.organization.tenant.dto.TenantDto;

@Service
@AllArgsConstructor
public class TenantFacade {

    private final TenantService service;
    private final TenantQueryRepository tenants;
    private final TenantRepository repository;

    private final TenantMapper mapper;

    public TenantDto handle( CreateTenantCommand command) {

        return tenants.findByCode( command.code())
                .orElseGet(()-> {
                    try {
                        service.create( command.code());
                        return mapper.toDto( repository.save( mapper.from( command)));
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });
    }

}
