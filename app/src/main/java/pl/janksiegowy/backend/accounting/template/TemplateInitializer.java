package pl.janksiegowy.backend.accounting.template;

import lombok.AllArgsConstructor;
import pl.janksiegowy.backend.accounting.template.dto.TemplateDto;
import pl.janksiegowy.backend.accounting.template.dto.TemplateMap;

import java.util.List;

@AllArgsConstructor
public class TemplateInitializer {

    private final TemplateQueryRepository templates;
    private final TemplateFacade facade;
    public void init( List<TemplateMap> initialTemplates) {

        initialTemplates.forEach( facade::save);

    }
}
