package pl.janksiegowy.backend.finances.notice.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Setter;
import lombok.experimental.Accessors;
import pl.janksiegowy.backend.entity.Country;
import pl.janksiegowy.backend.entity.dto.EntityDto;
import pl.janksiegowy.backend.finances.notice.NoteType;
import pl.janksiegowy.backend.shared.Util;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public interface NoticeDto {

    static Proxy create() {
        return new Proxy();
    }

    UUID getNoticeId();
    NoteType getType();
    String getNumber();
    LocalDate getDate();
    LocalDate getDue();
    EntityDto getEntity();
    String getEntityTaxNumber();
    Country getEntityCountry();
    BigDecimal getAmount();

    @Setter
    @Accessors( fluent= true, chain= true)
    class Proxy implements NoticeDto {

        private UUID noticeId;

        @JsonProperty( "Type")
        private NoteType type;

        @JsonProperty( "Number")
        private String number;

        @JsonProperty( "Date")
        private LocalDate date;

        @JsonProperty( "Due")
        private LocalDate due;

        private EntityDto entity;
        private Country entityCountry;
        private String entityTaxNumber;

        @JsonProperty( "Amount")
        private BigDecimal amount;

        @JsonProperty( "Tax Number")
        public Proxy entityTaxNumber( String entityTaxNumber) {
            var entity= Util.parseTaxNumber( entityTaxNumber, "PL");
            this.entityTaxNumber = entity.getTaxNumber();
            this.entityCountry = entity.getCountry();
            return this;
        }

        @Override public UUID getNoticeId() {
            return noticeId;
        }
        @Override public LocalDate getDate() {
            return date;
        }
        @Override public LocalDate getDue() {
            return due;
        }
        @Override public NoteType getType() {
            return type;
        }
        @Override  public String getNumber() {
            return number;
        }
        @Override public EntityDto getEntity() {
            return entity;
        }

        @Override public String getEntityTaxNumber() {
            return entityTaxNumber;
        }

        @Override
        public Country getEntityCountry() {
            return entityCountry;
        }

        @Override public BigDecimal getAmount() {
            return amount;
        }

    }
}
