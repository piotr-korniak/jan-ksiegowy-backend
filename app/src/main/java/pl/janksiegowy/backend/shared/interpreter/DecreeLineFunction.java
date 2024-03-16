package pl.janksiegowy.backend.shared.interpreter;

public enum DecreeLineFunction {

    Expression {
        @Override public <T> T accept( LineFunctionVisitor<T> visitor) {
            return visitor.visitExpression();
        }
    },
    TurnoverCt {
        @Override public <T> T accept( LineFunctionVisitor<T> visitor ) {
            return visitor.visitTurnoverCt();
        }
    },
    TurnoverDt {
        @Override public <T> T accept( LineFunctionVisitor<T> visitor ) {
            return visitor.visitTurnoverDt();
        }
    };

    public abstract <T> T accept( LineFunctionVisitor<T> visitor);
    public interface LineFunctionVisitor<T> {
        T visitExpression();
        T visitTurnoverCt();
        T visitTurnoverDt();

    }

}
