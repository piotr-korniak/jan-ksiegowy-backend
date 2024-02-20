package pl.janksiegowy.backend.shared.numerator.dto;

import lombok.Setter;
import lombok.experimental.Accessors;
import pl.janksiegowy.backend.shared.numerator.NumeratorType;

public interface NumeratorDto {

    static Proxy create() { return new Proxy();}

    Long getId();
    String getCode();
    String getName();
    String getMask();
    NumeratorType getType();

    @Setter
    @Accessors( fluent= true, chain= true)
    class Proxy implements NumeratorDto {
        private Long id;
        private String code;
        private String name;
        private String mask;
        private NumeratorType type;

        @Override public Long getId() {
            return id;
        }
        @Override public String getCode() {
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
    }
}
