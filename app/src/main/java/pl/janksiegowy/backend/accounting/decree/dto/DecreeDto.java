package pl.janksiegowy.backend.accounting.decree.dto;

import lombok.Setter;
import lombok.experimental.Accessors;
import pl.janksiegowy.backend.accounting.decree.DecreeType;
import pl.janksiegowy.backend.register.dto.RegisterDto;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface DecreeDto {

    static Proxy create() {
        return new Proxy();
    }

    UUID getDecreeId();
    LocalDate getDate();
    RegisterDto getRegister();
    List<DecreeLineDto> getLines();
    DecreeType getType();



    @Setter
    @Accessors( fluent= true, chain= true)
    class Proxy implements DecreeDto{

        private UUID degreeId;
        private LocalDate date;
        private RegisterDto register;
        private List<DecreeLineDto> lines;
        private DecreeType type;

        @Override public UUID getDecreeId() {
            return degreeId;
        }
        @Override public LocalDate getDate() {
            return date;
        }
        @Override public RegisterDto getRegister() {
            return register;
        }
        @Override public List<DecreeLineDto> getLines() {
            return lines;
        }
        @Override public DecreeType getType() {
            return type;
        }
    }
}
