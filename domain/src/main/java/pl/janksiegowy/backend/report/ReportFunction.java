package pl.janksiegowy.backend.report;

public enum ReportFunction {

    Label {
        @Override public <T> T accept( LineFunctionVisitor<T> visitor) {
            return visitor.visitLabel();
        }
    },
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
    },
    DevelopedBalanceDt {
        @Override public <T> T accept( LineFunctionVisitor<T> visitor ) {
            return visitor.visitDevelopedBalanceDt();
        }
    },
    DevelopedBalanceCt {
        @Override public <T> T accept( LineFunctionVisitor<T> visitor ) {
            return visitor.visitDevelopedBalanceCt();
        }
    },

    ParentBalanceDt {
        @Override public <T> T accept( LineFunctionVisitor<T> visitor) {
            return visitor.visitParentBalanceDt();
        }
    },
    BalanceCtLike {
        @Override public <T> T accept( LineFunctionVisitor<T> visitor) {
            return visitor.visitBalanceCtLike();
        }
    },
    BalanceDtLike {
        @Override public <T> T accept( LineFunctionVisitor<T> visitor) {
            return visitor.visitBalanceDtLike();
        }
    },
    Calculate {
        @Override public <T> T accept( LineFunctionVisitor<T> visitor ) {
            return visitor.visitCalculate();
        }
    };

    public abstract <T> T accept( LineFunctionVisitor<T> visitor);
    public interface LineFunctionVisitor<T> {
        T visitLabel();
        T visitExpression();
        T visitTurnoverCt();
        T visitTurnoverDt();
        T visitDevelopedBalanceDt();
        T visitDevelopedBalanceCt();
        T visitParentBalanceDt();
        T visitCalculate();
        T visitBalanceCtLike();
        T visitBalanceDtLike();
    }

}
