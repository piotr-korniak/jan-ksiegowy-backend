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

    OdliczenieVAT {
        @Override public <T> T accept(InvoiceFunctionVisitor<T> visitor) {
            return visitor.visitOdliczenieVAT();
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
    },
    KwotaPozostaleKUP {
        @Override public <T> T accept( InvoiceFunctionVisitor<T> visitor ) {
            return visitor.visitKwotaPozostaleKup();
        }
    };

    public abstract <T> T accept( InvoiceFunctionVisitor<T> visitor);

    public interface InvoiceFunctionVisitor<T> {
        T visitKwotaUslugKUP();
        T visitKwotaUslugNUP();
        T visitKwotaBrutto();
        T visitKwotaVAT();
        T visitOdliczenieVAT();
        T visitKwotaMaterialowKUP();
        T visitKwotaMaterialowNUP();
        T visitKwotaPozostaleKup();
    }
}
