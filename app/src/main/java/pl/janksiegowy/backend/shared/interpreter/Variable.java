package pl.janksiegowy.backend.shared.interpreter;

import java.awt.desktop.OpenFilesEvent;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;

public class Variable implements Expression {
    private String name;

    public Variable( String name) {
        this.name= name;
    }

    @Override public BigDecimal interpret( Map<String, BigDecimal> variables) {
        return Optional.ofNullable( variables.get( name))
                .orElseThrow( ()-> new RuntimeException( "Undefined variable: "+ name));
    }
}
