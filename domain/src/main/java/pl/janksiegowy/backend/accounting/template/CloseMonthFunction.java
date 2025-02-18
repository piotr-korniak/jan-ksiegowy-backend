package pl.janksiegowy.backend.accounting.template;

public enum CloseMonthFunction {
    WynagrodzenieNetto {
        @Override public <T> T accept( CloseMonthFunctionVisitor<T> visitor ) {
            return visitor.visitWynagrodzenieNetto();
        }
    },

    UbezpieczenieZUS {
        @Override public <T> T accept( CloseMonthFunctionVisitor<T> visitor ) {
            return visitor.visitUbezpieczeniaZUS();
        }
    };

    public abstract <T> T accept( CloseMonthFunctionVisitor<T> visitor);

    public interface CloseMonthFunctionVisitor<T> {
        T visitWynagrodzenieNetto();
        T visitUbezpieczeniaZUS();
    }

}
