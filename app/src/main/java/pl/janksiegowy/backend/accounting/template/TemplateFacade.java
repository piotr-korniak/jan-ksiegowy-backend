package pl.janksiegowy.backend.accounting.template;

import lombok.AllArgsConstructor;
import pl.janksiegowy.backend.accounting.template.dto.TemplateDto;

import java.util.Optional;

@AllArgsConstructor
public class TemplateFacade {

    private final TemplateRepository repository;
    private final TemplateFactory factory;

    public Template save( TemplateDto source) {

        return repository.save( Optional.ofNullable( source.getTemplateId())
                .map( templateId-> repository.findTemplateByTemplateIdAndDate( templateId, source.getDate())
                    .map( template-> factory.update( source, template))     // Update Template history
                    .orElseGet(()-> factory.update( source)))               // New Template history
                .orElseGet(()-> factory.from( source)));                    // New Template

    }

}
