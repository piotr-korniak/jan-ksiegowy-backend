package pl.janksiegowy.backend.organization;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.janksiegowy.backend.organization.command.RegisterTenantWithInitialCompanyCommand;
import pl.janksiegowy.backend.organization.usecase.RegisterTenantWithInitialCompanyUseCase;

@Service
@RequiredArgsConstructor
public class OrganizationFacade {

    private final RegisterTenantWithInitialCompanyUseCase registerTenantWithInitialCompanyUseCase;
   // private final RegisterCompanyInTenantUseCase registerCompanyInTenantUseCase;

    public String registerTenantWithInitialCompany(
            RegisterTenantWithInitialCompanyCommand command
    ) {
        return registerTenantWithInitialCompanyUseCase.handle( command);
    }

}
