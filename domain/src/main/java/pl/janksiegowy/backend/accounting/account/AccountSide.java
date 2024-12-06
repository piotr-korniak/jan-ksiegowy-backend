package pl.janksiegowy.backend.accounting.account;

public enum AccountSide {
    D {
        @Override public <T> T accept( AccountPageVisitor<T> visitor ) {
            return visitor.visitDtPage();
        }
    }, //  DT
    C {
        @Override public <T> T accept( AccountPageVisitor<T> visitor ) {
            return visitor.visitCtPage();
        }
    }; // CT

    public abstract <T> T accept( AccountPageVisitor<T> visitor);

    public interface AccountPageVisitor<T> {
        T visitDtPage();
        T visitCtPage();
    }
}
