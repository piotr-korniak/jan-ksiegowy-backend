package pl.janksiegowy.backend.organization.company;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.janksiegowy.backend.organization.command.CreateCompanyCommand;
import pl.janksiegowy.backend.organization.company.dto.CompanyDto;

@Service
@AllArgsConstructor
public class CompanyFacade {
    private final CompanyService service;
    private final CompanyQueryRepository companies;
    private final CompanyRepository repository;

    private final CompanyMapper mapper;

    public CompanyDto handle( CreateCompanyCommand command) {
        return companies.findByCode( command.code())
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
