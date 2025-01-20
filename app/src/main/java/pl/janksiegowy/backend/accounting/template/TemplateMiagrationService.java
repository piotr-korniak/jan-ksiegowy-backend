package pl.janksiegowy.backend.accounting.template;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import pl.janksiegowy.backend.accounting.template.dto.TemplateMap;

import java.util.List;

@Log4j2

@Service
@AllArgsConstructor
public class TemplateMiagrationService {

    private final TemplateQueryRepository templates;
    private final TemplateFacade facade;
    public String init( List<TemplateMap> initialTemplates) {

        initialTemplates.forEach( templateMap-> {
            if( templates.findByCode( templateMap.getCode())
                    .map( i-> i.getDate().isBefore( templateMap.getDate()))
                    .orElseGet( ()->true))
                facade.save( templateMap);
        });

        log.warn( "Templates migration complete!");
        return "Templates migration complete!";
    }
}
