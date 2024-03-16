package pl.janksiegowy.backend.shared.pattern.dto;

import lombok.Setter;
import lombok.experimental.Accessors;
import pl.janksiegowy.backend.shared.pattern.PatternCode;
import pl.janksiegowy.backend.shared.pattern.PatternId;

import java.time.LocalDate;

public interface PatternDto {
    static Proxy create() {
        return new Proxy();
    }

    PatternId getPatternId();
    PatternCode getPatternCode();
    LocalDate getDate();
    String getName();

    @Setter
    @Accessors( fluent= true, chain= true)
    class Proxy implements PatternDto {

        private PatternId patternId;
        private PatternCode patternCode;
        private LocalDate date;
        private String name;

        @Override public PatternId getPatternId() {
            return patternId;
        }
        @Override public PatternCode getPatternCode() {
            return patternCode;
        }
        @Override public LocalDate getDate() {
            return date;
        }
        @Override public String getName() {
            return name;
        }

    }
}
