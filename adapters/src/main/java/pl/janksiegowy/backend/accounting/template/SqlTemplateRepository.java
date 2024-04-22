package pl.janksiegowy.backend.accounting.template;

import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import pl.janksiegowy.backend.accounting.template.dto.TemplateDto;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

public interface SqlTemplateRepository extends JpaRepository<Template, UUID> {

    @Query( value= "FROM Template M " +
            "LEFT OUTER JOIN Template P "+
            "ON M.templateId= P.templateId AND (P.date <= :date AND M.date < P.date) "+
            "WHERE M.documentType= :type AND M.date <= :date AND P.date IS NULL")
    Optional<Template> findByTypeAndKindAndDate( TemplateType type, LocalDate date);


    Optional<Template> findTemplateByTemplateIdAndDate( UUID templateId, LocalDate date );
}

interface SqlTemplateQueryRepository extends TemplateQueryRepository, Repository<Template, UUID> {

    @Override
    @Query( value= "SELECT T " +
            "FROM Template T " +
            "LEFT OUTER JOIN Template P "+
            "ON T.templateId= P.templateId AND T.date < P.date "+
            "WHERE T.code= :code AND P.date IS NULL")
    Optional<TemplateDto> findByCode( String code);
}

@org.springframework.stereotype.Repository
@AllArgsConstructor
class TemplateRepositoryImpl implements TemplateRepository {

    private final SqlTemplateRepository repository;
    @Override public Template save( Template template) {
        return repository.save( template);
    }

    @Override public Optional<Template> findTemplateByTemplateIdAndDate( UUID templateId, LocalDate date ) {
        return repository.findTemplateByTemplateIdAndDate( templateId, date);
    }

    @Override public Optional<Template> findByDocumentTypeAndDate( TemplateType type, LocalDate date ) {
        return repository.findByTypeAndKindAndDate( type, date);
    }
}
