package pl.janksiegowy.backend.accounting.template;

import pl.janksiegowy.backend.entity.EntityType;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

public interface TemplateRepository {
    Template save( Template template);

    Optional<Template> findTemplateByTemplateIdAndDate( UUID templateId, LocalDate date );
    Optional<Template> findByDocumentTypeAndDate( TemplateType type, LocalDate date );

    Optional<Template> findByDocumentTypeAndDateAndPaymentType(
            TemplateType type, LocalDate date, EntityType entityType);
}
