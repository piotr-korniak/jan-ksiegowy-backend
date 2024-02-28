package pl.janksiegowy.backend.accounting.template;

import pl.janksiegowy.backend.finances.payment.PaymentType;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

public interface TemplateRepository {
    Template save( Template template);

    Optional<Template> findTemplateByTemplateIdAndDate( UUID templateId, LocalDate date );
    Optional<Template> findByTypeAndKindAndDate( TemplateType type, String kind, LocalDate date );
}
