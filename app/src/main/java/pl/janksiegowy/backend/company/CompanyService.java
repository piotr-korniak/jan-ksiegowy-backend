package pl.janksiegowy.backend.company;

import pl.janksiegowy.backend.company.dto.CompanyDto;

public interface CompanyService {
    void create(String companyCode);

    void save(CompanyDto source);

}
