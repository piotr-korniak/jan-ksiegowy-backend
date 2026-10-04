package pl.janksiegowy.backend.organization.company;

import pl.janksiegowy.backend.organization.company.dto.CompanyDto;

import java.util.Optional;

public interface CompanyQueryRepository {
    boolean existsByCode( String code);
    Optional<CompanyDto> findByCode( String code);

}
