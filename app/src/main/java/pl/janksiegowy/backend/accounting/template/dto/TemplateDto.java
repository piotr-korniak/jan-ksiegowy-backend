package pl.janksiegowy.backend.accounting.template.dto;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Setter;
import lombok.experimental.Accessors;
import pl.janksiegowy.backend.accounting.template.TemplateType;
import pl.janksiegowy.backend.entity.EntityType;
import pl.janksiegowy.backend.register.RegisterQueryRepository;
import pl.janksiegowy.backend.register.dto.RegisterDto;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

public interface TemplateDto {
    static Proxy create() {
        return new Proxy();
    }

    UUID getTemplateId();
    LocalDate getDate();
    String getCode();
    TemplateType getDocumentType();
    UUID getRegisterRegisterId();
    List<? extends TemplateLineDto> getLines();
    String getName();

    EntityType getEntityType();

    @Setter
    @Accessors( fluent= true, chain= true)
    class Proxy implements TemplateDto {

        public Proxy() {}

        @JsonCreator
        public Proxy( @JsonProperty( "registerCode") String registerCode,
                      @JacksonInject RegisterQueryRepository registers) {
            registerId= registers.findAccountRegisterByCode( registerCode)
                    .map( RegisterDto::getRegisterId)
                    .orElseThrow(()-> new NoSuchElementException( "Register not found: "+ registerCode));
        }

        private UUID templateId;
        private LocalDate date;
        private String code;
        private TemplateType documentType;
        private EntityType entityType;
        private List<TemplateLineDto.Proxy> lines;
        private UUID registerId;
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
        @Override public UUID getRegisterRegisterId() {
            return registerId;
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
