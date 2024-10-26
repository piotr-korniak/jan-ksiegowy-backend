package pl.janksiegowy.backend.accounting.template.dto;

import pl.janksiegowy.backend.accounting.template.TemplateType;
import pl.janksiegowy.backend.entity.EntityType;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TemplateMap implements TemplateDto{

    private final TemplateDto template;
    private final List<TemplateLineDto> lines;

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
        return lines;
    }
    @Override public String getName() {
        return template.getName();
    }
    @Override
    public EntityType getEntityType() {
        return template.getEntityType();
    }
}
