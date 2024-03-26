package pl.janksiegowy.backend.shared.interpreter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

public class Multiplication implements Expression {
    private Expression left;
    private Expression right;
    private int scale;

    public Multiplication( Expression left, Variable right, int scale) {
        this.left= left;
        this.right= right;
        this.scale= scale;
    }

    @Override public BigDecimal interpret( Map<String, BigDecimal> variables ) {
        var left= this.left.interpret( variables);
        var right= this.right.interpret( variables);

        return left.multiply( right).setScale( scale, RoundingMode.HALF_UP);
    }
}
