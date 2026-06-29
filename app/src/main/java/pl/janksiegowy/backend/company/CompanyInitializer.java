package pl.janksiegowy.backend.company;

import lombok.AllArgsConstructor;
import pl.janksiegowy.backend.company.dto.CompanyDto;

@AllArgsConstructor
public class CompanyInitializer {

    private final CompanyFacade company;

    public void init() {

        company.create( CompanyDto.create()
                .code( "pl5882534461")
                .name( "Eleutheria Rachunkowość Sp. z o.o."));
    }
}
