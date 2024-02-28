package pl.janksiegowy.backend.accounting.account;

public enum AccountType {
    B { // balance account
        @Override public <T> T accept( AccountTypeVisitor<T> visitor) {
            return visitor.visitBalanceAccount();
        }
    },
    S { // settlement account
        @Override public <T> T accept( AccountTypeVisitor<T> visitor) {
            return visitor.visitSettlementAccount();
        }
    },
    P { // profit and loss account
        @Override public <T> T accept( AccountTypeVisitor<T> visitor) {
            return visitor.visitProfiAndLossAccount();
        }
    };

    public abstract <T> T accept( AccountTypeVisitor<T> visitor);

    public interface AccountTypeVisitor<T> {
        T visitBalanceAccount();
        T visitSettlementAccount();
        T visitProfiAndLossAccount();
    }
}
