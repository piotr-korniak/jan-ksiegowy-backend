package pl.janksiegowy.backend.accounting.template;

public enum InvoiceFunction {
    KwotaUslugKUP {
        @Override public <T> T accept( InvoiceFunctionVisitor<T> visitor ) {
            return visitor.visitKwotaUslugKUP();
        }
    },
    KwotaUslugNUP {
        @Override public <T> T accept( InvoiceFunctionVisitor<T> visitor ) {
            return visitor.visitKwotaUslugNUP();
        }
    },
    KwotaBurtto {
        @Override public <T> T accept( InvoiceFunctionVisitor<T> visitor ) {
            return visitor.visitKwotaBrutto();
        }
    },

    KwotaVAT {
        @Override public <T> T accept( InvoiceFunctionVisitor<T> visitor ) {
            return visitor.visitKwotaVAT();
        }
    },

    KwotaMaterialowKUP {
        @Override public <T> T accept( InvoiceFunctionVisitor<T> visitor ) {
            return visitor.visitKwotaMaterialowKUP();
        }
    },

    KwotaMaterialowNUP {
        @Override public <T> T accept( InvoiceFunctionVisitor<T> visitor ) {
            return visitor.visitKwotaMaterialowNUP();
        }
    };

    public abstract <T> T accept( InvoiceFunctionVisitor<T> visitor);

    public interface InvoiceFunctionVisitor<T> {
        T visitKwotaUslugKUP();
        T visitKwotaUslugNUP();
        T visitKwotaBrutto();
        T visitKwotaVAT();
        T visitKwotaMaterialowKUP();
        T visitKwotaMaterialowNUP();
    }
}
