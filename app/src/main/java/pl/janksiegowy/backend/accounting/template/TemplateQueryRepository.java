package pl.janksiegowy.backend.accounting.template;

import pl.janksiegowy.backend.accounting.template.dto.TemplateDto;
import java.util.Optional;

public interface TemplateQueryRepository {

    Optional<TemplateDto> findByCode( String code);
}
