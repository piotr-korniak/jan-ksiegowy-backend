package pl.janksiegowy.backend.accounting.template;

public enum PayslipFunction {
    SkladkaPracownika {
        @Override public <T> T accept( PayslipFunctionVisitor<T> visitor) {
            return visitor.visitSkladkaPracownika();
        }
    },
    SkladkaPracodawcy {
        @Override public <T> T accept( PayslipFunctionVisitor<T> visitor ) {
            return visitor.visitSkladkaPracodawcy();
        }
    },
    WynagrodzenieBrutto {
        @Override public <T> T accept( PayslipFunctionVisitor<T> visitor ) {
            return visitor.visitWynagrodzenieBrutto();
        }
    },
    UbezpieczenieZdrowotne {
        @Override public <T> T accept( PayslipFunctionVisitor<T> visitor ) {
            return visitor.visitUbezpiecznieZdrowotne();
        }
    },
    ZaliczkaPIT {
        @Override public <T> T accept( PayslipFunctionVisitor<T> visitor ) {
            return visitor.visitZaliczkaPIT();
        }
    },
    DoWyplaty {
        @Override public <T> T accept( PayslipFunctionVisitor<T> visitor ) {
            return visitor.visitDoWyplaty();
        }
    };

    public abstract <T> T accept( PayslipFunctionVisitor<T> visitor);

    public interface PayslipFunctionVisitor<T> {
        T visitSkladkaPracownika();
        T visitSkladkaPracodawcy();
        T visitWynagrodzenieBrutto();
        T visitUbezpiecznieZdrowotne();
        T visitZaliczkaPIT();
        T visitDoWyplaty();

    }
}
