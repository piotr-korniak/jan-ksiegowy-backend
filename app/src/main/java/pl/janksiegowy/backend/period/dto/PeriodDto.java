package pl.janksiegowy.backend.period.dto;

import lombok.Setter;
import lombok.experimental.Accessors;
import pl.janksiegowy.backend.period.PeriodType;

import java.time.LocalDate;

public interface PeriodDto {

    static Proxy create() {
        return new Proxy();
    }

    String getId();
    LocalDate getBegin();
    LocalDate getEnd();
    PeriodType getType();

    @Setter
    @Accessors( fluent= true, chain= true)
    class Proxy implements PeriodDto {

        private String id;
        private LocalDate begin;
        private LocalDate end;
        private PeriodType type;

        @Override public String getId() {
            return id;
        }

        @Override public LocalDate getBegin() {
            return begin;
        }

        @Override public LocalDate getEnd() {
            return end;
        }

        @Override public PeriodType getType() {
            return type;
        }
    }

}
