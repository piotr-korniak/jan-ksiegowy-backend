package pl.janksiegowy.backend.shared.interpreter;

import java.math.BigDecimal;
import java.util.Map;

public interface Expression {
    BigDecimal interpret( Map<String, BigDecimal> variables);
}
