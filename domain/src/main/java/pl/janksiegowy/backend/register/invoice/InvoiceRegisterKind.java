package pl.janksiegowy.backend.register.invoice;

import pl.janksiegowy.backend.entity.EntityType;

public enum InvoiceRegisterKind {
    D { // Domestic
        @Override public <T> T accept( VatRegisterTypeVisitor<T> visitor) {
            return visitor.visitDomestic();
        }
    },
    U { // Union
        @Override public <T> T accept( VatRegisterTypeVisitor<T> visitor) {
            return visitor.visitUnion();
        }
    },
    W { // World
        @Override public <T> T accept( VatRegisterTypeVisitor<T> visitor) {
            return visitor.visitWorld();
        }
    };

    public abstract <T> T accept( VatRegisterTypeVisitor<T> visitor);

    public interface VatRegisterTypeVisitor<T> {
        T visitDomestic();
        T visitUnion();
        T visitWorld();
    }

}
