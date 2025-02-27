package pl.janksiegowy.backend.shared.interpreter;

import java.math.BigDecimal;
import java.util.Map;

public class Maximum implements Expression {
    private Expression left;
    private Expression right;

    public Maximum( Expression left, Expression right) {
        this.left= left;
        this.right= right;
    }

    @Override public BigDecimal interpret( Map<String, BigDecimal> variables) {
        var left= this.left.interpret( variables);
        var right= this.right.interpret( variables);

        return left.max( right);
    }
}
