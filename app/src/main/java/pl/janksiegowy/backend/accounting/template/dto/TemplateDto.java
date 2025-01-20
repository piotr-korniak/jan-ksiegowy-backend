package pl.janksiegowy.backend.accounting.template.dto;

import lombok.Setter;
import lombok.experimental.Accessors;
import pl.janksiegowy.backend.accounting.account.AccountType;
import pl.janksiegowy.backend.accounting.template.TemplateType;
import pl.janksiegowy.backend.entity.EntityType;
import pl.janksiegowy.backend.entity.dto.EntityDto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public interface TemplateDto {
    static Proxy create() {
        return new Proxy();
    }

    UUID getTemplateId();
    LocalDate getDate();
    String getCode();
    TemplateType getDocumentType();
    String getRegisterCode();
    List<? extends TemplateLineDto> getLines();
    String getName();

    EntityType getEntityType();

    @Setter
    @Accessors( fluent= true, chain= true)
    class Proxy implements TemplateDto {

        private UUID templateId;
        private LocalDate date;
        private String code;
        private TemplateType documentType;
        private EntityType entityType;
        private List<TemplateLineDto.Proxy> lines;
        private String registerCode;
        private String name;

        @Override public UUID getTemplateId() {
            return templateId;
        }
        @Override public LocalDate getDate() {
            return date;
        }
        @Override public String getCode() {
            return code;
        }
        @Override public TemplateType getDocumentType() {
            return documentType;
        }
        @Override public String getRegisterCode() {
            return registerCode;
        }
        @Override public List<? extends TemplateLineDto> getLines() {
            return lines;
        }
        @Override public String getName() {
            return name;
        }
        @Override public EntityType getEntityType() {
            return entityType;
        }
    }

}
