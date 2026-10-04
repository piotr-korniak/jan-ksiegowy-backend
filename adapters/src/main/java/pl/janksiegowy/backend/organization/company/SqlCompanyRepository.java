package pl.janksiegowy.backend.organization.company;

import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;
import pl.janksiegowy.backend.organization.company.dto.CompanyDto;

import java.util.Optional;
import java.util.UUID;

public interface SqlCompanyRepository extends JpaRepository<Company, UUID> {
}

@org.springframework.stereotype.Repository
interface SqlCompanyQueryRepository extends CompanyQueryRepository, Repository<Company, UUID> {

    Optional<CompanyDto> findByCode( String code);
}

@org.springframework.stereotype.Repository
@AllArgsConstructor
class CompanyRepositoryImpl implements CompanyRepository {

    private final SqlCompanyRepository repository;

    @Override public Company save( Company company) {
        return repository.save( company);
    }
}
