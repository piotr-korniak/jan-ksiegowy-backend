package pl.janksiegowy.backend.accounting.decree.dto;

import pl.janksiegowy.backend.accounting.decree.DecreeType;
import pl.janksiegowy.backend.register.dto.RegisterDto;
import pl.janksiegowy.backend.shared.Util;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class DecreeMap implements DecreeDto {

    private BigDecimal ct;
    private BigDecimal dt;

    private final DecreeDto decree;
    private final List<DecreeLineDto> lines;

    public DecreeMap( DecreeDto decree) {
        this.decree= decree;
        this.lines= Optional.ofNullable( decree.getLines()).orElseGet( ArrayList::new);
    }

    public DecreeMap add( DecreeLineDto line) {
        switch (line.getSide()) {
            case D -> ct= Util.addOrAddend( ct, line.getValue());
            case C -> dt= Util.addOrAddend( dt, line.getValue());
        }
        lines.add( line);
        return this;
    }

    public boolean isBalances() {
        if( ct== null)
            return false;
        if( dt== null)
            return false;

        return ct.compareTo( dt)== 0;
    }

    @Override public UUID getDecreeId() {
        return decree.getDecreeId();
    }

    @Override public LocalDate getDate() {
        return decree.getDate();
    }

    @Override public String getNumber() {
        return decree.getNumber();
    }

    @Override public RegisterDto getRegister() {
        return decree.getRegister();
    }

    @Override public DecreeType getType() {
        return decree.getType();
    }

    @Override public String getDocument() {
        return decree.getDocument();
    }

    @Override public List<DecreeLineDto> getLines() {
        return lines;
    }

    public DecreeMap setNumer( String number ) {
        ((DecreeDto.Proxy)decree).number( number);
        return this;
    }
}
