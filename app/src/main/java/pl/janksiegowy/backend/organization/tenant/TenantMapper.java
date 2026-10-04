package pl.janksiegowy.backend.organization.tenant;

import org.springframework.stereotype.Component;
import pl.janksiegowy.backend.organization.command.CreateTenantCommand;
import pl.janksiegowy.backend.organization.tenant.dto.TenantDto;

@Component
public class TenantMapper {

    public Tenant from( CreateTenantCommand command) {
        return new Tenant()
                .setName(command.name())
                .setCode( command.code());
    }

    public TenantDto toDto( Tenant source) {
        return TenantDto.create()
                .id( source.getId())
                .name( source.getName())
                .code( source.getCode());
    }
}
