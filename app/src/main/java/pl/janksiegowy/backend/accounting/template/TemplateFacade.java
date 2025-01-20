package pl.janksiegowy.backend.accounting.template;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import pl.janksiegowy.backend.accounting.template.dto.TemplateDto;
import pl.janksiegowy.backend.shared.MigrationService;

import java.util.Optional;

@Log4j2

@AllArgsConstructor
public class TemplateFacade {

    private final TemplateRepository repository;
    private final TemplateQueryRepository templates;
    private final TemplateFactory factory;
    private final MigrationService migrationService;

    public Template save( TemplateDto source) {

        return repository.save( Optional.ofNullable( source.getTemplateId())
                .map( templateId-> repository.findTemplateByTemplateIdAndDate( templateId, source.getDate())
                    .map( template-> factory.update( source, template)) // Update Template history
                    .orElseGet(()-> factory.from( source)))                      // New Template history
                .orElseGet(()-> factory.from( source)));                         // New Template

    }

    public String migrate() {
        int[] counters= { 0, 0};

        migrationService.loadTemplates()
            .forEach( templateDto-> {
                counters[0]++;

                if( templates.findByCode(templateDto.getCode())
                        .filter(i -> i.getDate().isBefore(templateDto.getDate())).isPresent()) {
                    save(templateDto);
                    counters[1]++;
                }
            });

        log.warn( "Templates migration complete!");
        return String.format( "%-50s %13s", "Templates migration complete, added: ", counters[1]+ "/"+ counters[0]);
    }

}
