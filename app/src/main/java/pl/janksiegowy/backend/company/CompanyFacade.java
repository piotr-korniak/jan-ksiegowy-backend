package pl.janksiegowy.backend.company;

import lombok.AllArgsConstructor;
import pl.janksiegowy.backend.company.dto.CompanyDto;

@AllArgsConstructor
public class CompanyFacade {
    private final CompanyService service;
    private final CompanyQueryRepository companies;

    public void create( CompanyDto source) {

        if( companies.existsByCode( source.getCode()))
            return;

        try {
            System.out.println( "ERROR: create!");
            service.create( source.getCode());

            System.out.println( "ERROR: save!");
            service.save( source);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
