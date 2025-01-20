package pl.janksiegowy.backend.accounting.template;

public enum PayslipFunction {
    UbezpieczenieEmerytalneZatrudniony {
        @Override public <T> T accept( PayslipFunctionVisitor<T> visitor) {
            return visitor.visitUbezpieczenieEmerytalneZatrudniony();
        }
    },

    WynagrodzenieBrutto {
        @Override public <T> T accept( PayslipFunctionVisitor<T> visitor ) {
            return visitor.visitWynagrodzenieBrutto();
        }
    },
    UbezpieczenieZdrowotne {
        @Override public <T> T accept( PayslipFunctionVisitor<T> visitor ) {
            return visitor.visitUbezpieczenieZdrowotne();
        }
    },
    KwotaZaliczki {
        @Override public <T> T accept( PayslipFunctionVisitor<T> visitor ) {
            return visitor.visitKwotaZaliczki();
        }
    },
    DoWyplaty {
        @Override public <T> T accept( PayslipFunctionVisitor<T> visitor ) {
            return visitor.visitDoWyplaty();
        }
    };

    public abstract <T> T accept( PayslipFunctionVisitor<T> visitor);

    public interface PayslipFunctionVisitor<T> {
        T visitUbezpieczenieEmerytalneZatrudniony();
        T visitWynagrodzenieBrutto();
        T visitUbezpieczenieZdrowotne();
        T visitKwotaZaliczki();
        T visitDoWyplaty();

    }
}
