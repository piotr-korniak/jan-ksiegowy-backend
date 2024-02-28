package pl.janksiegowy.backend.accounting.decree;

import pl.janksiegowy.backend.accounting.account.AccountType;

public enum DecreeType {
    B { // Basic decree
        @Override public <T> T accept( DecreeTypeVisitor<T> visitor) {
            return visitor.visitBasicDecree();
        }
    },
    S { // Settlement decree
        @Override public <T> T accept( DecreeTypeVisitor<T> visitor) {
            return visitor.visitSettlementDecree();
        }
    };

    public abstract <T> T accept( DecreeTypeVisitor<T> visitor);

    public interface DecreeTypeVisitor<T> {
        T visitBasicDecree();
        T visitSettlementDecree();

    }
}
