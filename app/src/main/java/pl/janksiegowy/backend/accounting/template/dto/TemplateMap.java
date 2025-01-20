package pl.janksiegowy.backend.accounting.template.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import pl.janksiegowy.backend.accounting.template.TemplateType;
import pl.janksiegowy.backend.entity.EntityType;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class TemplateMap implements TemplateDto{

    private TemplateDto template;

    private List<TemplateLineDto> lines= new ArrayList<>();

        public TemplateMap( TemplateDto template) {
        this.template= template;
        this.lines= new ArrayList<>();
    }

    public TemplateMap add( TemplateLineDto line) {
        this.lines.add( line);
        return this;
    }


    @Override public UUID getTemplateId() {
        return template.getTemplateId();
    }
    @Override public LocalDate getDate() {
        return template.getDate();
    }
    @Override public String getCode() {
        return template.getCode();
    }
    @Override public TemplateType getDocumentType() {
        return template.getDocumentType();
    }
    @Override public String getRegisterCode() {
        return template.getRegisterCode();
    }

    @Override public List<TemplateLineDto> getLines() {
        return lines.stream().map( proxy -> (TemplateLineDto)proxy)
                .collect( Collectors.toList());
    }
    @Override public String getName() {
        return template.getName();
    }
    @Override
    public EntityType getEntityType() {
        return template.getEntityType();
    }
}
