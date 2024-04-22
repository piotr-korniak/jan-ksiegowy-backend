package pl.janksiegowy.backend.accounting.template.dto;

import lombok.Setter;
import lombok.experimental.Accessors;
import pl.janksiegowy.backend.accounting.template.TemplateType;

import java.time.LocalDate;
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
    List<TemplateLineDto> getLines();
    String getName();

    @Setter
    @Accessors( fluent= true, chain= true)
    class Proxy implements TemplateDto {

        private UUID templateId;
        private LocalDate date;
        private String code;
        private TemplateType documentType;
        private List<TemplateLineDto> items;
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
        @Override public List<TemplateLineDto> getLines() {
            return items;
        }
        @Override public String getName() {
            return name;
        }

    }

}
