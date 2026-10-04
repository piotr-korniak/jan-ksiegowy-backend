package pl.janksiegowy.backend.organization.company;

import org.springframework.stereotype.Component;
import pl.janksiegowy.backend.organization.command.CreateCompanyCommand;
import pl.janksiegowy.backend.organization.company.dto.CompanyDto;

@Component
public class CompanyMapper {

    public Company from( CreateCompanyCommand command) {
        return new Company()
                .setCode( command.code())
                .setName( command.name());
    }

    public CompanyDto toDto( Company source) {
        return CompanyDto.create()
                .id( source.getId())
                .code( source.getCode())
                .name( source.getName());
    }
}
