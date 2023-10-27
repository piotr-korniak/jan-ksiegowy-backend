package pl.janksiegowy.backend.company;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;
import pl.janksiegowy.backend.company.dto.CompanyDto;

import java.util.Optional;
import java.util.UUID;

public interface SqlCompanyRepository extends JpaRepository<Company, UUID> {
}

@org.springframework.stereotype.Repository
interface SqlCompanyQueryRepository extends CompanyQueryRepository, Repository<Company, UUID> {

    Optional<CompanyDto> findByCode( String code);
}
