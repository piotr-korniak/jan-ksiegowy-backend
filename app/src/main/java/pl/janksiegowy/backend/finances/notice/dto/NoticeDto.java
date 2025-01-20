package pl.janksiegowy.backend.finances.notice.dto;

import lombok.Setter;
import lombok.experimental.Accessors;
import pl.janksiegowy.backend.entity.dto.EntityDto;
import pl.janksiegowy.backend.finances.notice.NoteType;

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
    String getTaxNumber();
    BigDecimal getAmount();

    @Setter
    @Accessors( fluent= true, chain= true)
    class Proxy implements NoticeDto {

        private UUID noticeId;
        private NoteType type;
        private String number;
        private LocalDate date;
        private LocalDate due;
        private EntityDto entity;
        private String taxNumber;
        private BigDecimal amount;

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
        @Override public String getTaxNumber() {
            return taxNumber;
        }
        @Override public BigDecimal getAmount() {
            return amount;
        }

    }
}
