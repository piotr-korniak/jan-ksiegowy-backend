package pl.janksiegowy.backend.register.invoice;

import com.fasterxml.jackson.annotation.JsonCreator;

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

    @JsonCreator
    public static InvoiceRegisterKind fromString( String value) {
        if( value==null || value.isEmpty()) {
            return null;
        }
        return InvoiceRegisterKind.valueOf( value);
    }

    public interface VatRegisterTypeVisitor<T> {
        T visitDomestic();
        T visitUnion();
        T visitWorld();
    }

}
