package pl.janksiegowy.backend.accounting.decree.dto;

import pl.janksiegowy.backend.accounting.decree.DecreeType;
import pl.janksiegowy.backend.register.dto.RegisterDto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DecreeMap implements DecreeDto {

    private final DecreeDto decree;
    private final List<DecreeLineDto> lines;

    public DecreeMap( DecreeDto decree) {
        this.decree= decree;
        this.lines= new ArrayList<>();// decree.getLines());
    }

    public DecreeMap add( DecreeLineDto line) {
        lines.add( line);
        return this;
    }

    @Override public UUID getDecreeId() {
        return decree.getDecreeId();
    }

    @Override public LocalDate getDate() {
        return decree.getDate();
    }

    @Override public RegisterDto getRegister() {
        return decree.getRegister();
    }

    @Override public DecreeType getType() {
        return decree.getType();
    }
    @Override public List<DecreeLineDto> getLines() {
        return lines;
    }


}
