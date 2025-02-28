package pl.janksiegowy.backend.shared.numerator.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
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

        @JsonProperty( "Code")
        private NumeratorCode code;

        @JsonProperty( "Name")
        private String name;

        @JsonProperty( "Mask")
        private String mask;

        @JsonProperty( "Type")
        private NumeratorType type;

        @JsonProperty( "Typed")
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
