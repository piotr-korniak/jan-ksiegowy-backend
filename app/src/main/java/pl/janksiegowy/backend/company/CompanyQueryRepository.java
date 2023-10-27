package pl.janksiegowy.backend.company;

import pl.janksiegowy.backend.company.dto.CompanyDto;

import java.util.Optional;

public interface CompanyQueryRepository {
    boolean existsByCode( String code);
    Optional<CompanyDto> findByCode( String code);

}
