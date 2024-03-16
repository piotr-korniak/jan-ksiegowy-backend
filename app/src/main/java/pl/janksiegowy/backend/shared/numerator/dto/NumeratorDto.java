package pl.janksiegowy.backend.shared.numerator.dto;

import lombok.Setter;
import lombok.experimental.Accessors;
import pl.janksiegowy.backend.shared.numerator.NumeratorCode;
import pl.janksiegowy.backend.shared.numerator.NumeratorType;

import java.util.UUID;

public interface NumeratorDto {

    static Proxy create() { return new Proxy();}

    UUID getNumeratorId();
    NumeratorCode getCode();
    String getName();
    String getMask();
    NumeratorType getType();
    boolean isTyped();

    @Setter
    @Accessors( fluent= true, chain= true)
    class Proxy implements NumeratorDto {
        private UUID numeratorId;
        private NumeratorCode code;
        private String name;
        private String mask;
        private NumeratorType type;
        private boolean typed;

        @Override public UUID getNumeratorId() {
            return numeratorId;
        }
        @Override public NumeratorCode getCode() {
            return code;
        }
        @Override public String getName() {
            return name;
        }
        @Override public String getMask() {
            return mask;
        }
        @Override public NumeratorType getType() {
            return type;
        }
        @Override public boolean isTyped() {
            return typed;
        }
    }
}
