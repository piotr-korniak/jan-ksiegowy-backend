package pl.janksiegowy.backend.accounting.template;

public enum PayslipFunction {
    UbezpieczenieEmerytalneZatrudniony {
        @Override public <T> T accept( PayslipFunctionVisitor<T> visitor) {
            return visitor.visitUbezpieczenieEmerytalneZatrudniony();
        }
    },

    UbezpieczenieEmerytalnePracodawca {
        @Override public <T> T accept( PayslipFunctionVisitor<T> visitor) {
            return visitor.visitUbezpieczenieEmerytalnePracodawca();
        }
    },

    UbezpieczenieRentoweZatrudniony {
        @Override public <T> T accept( PayslipFunctionVisitor<T> visitor) {
            return visitor.visitUbezpieczenieRentoweZatrudniony();
        }
    },

    UbezpieczenieRentowePracodawca {
        @Override public <T> T accept( PayslipFunctionVisitor<T> visitor) {
            return visitor.visitUbezpieczenieRentowePracodawca();
        }
    },

    UbezpieczenieChoroboweZatrudniony {
        @Override public <T> T accept( PayslipFunctionVisitor<T> visitor) {
            return visitor.visitUbezpieczenieChoroboweZatrudniony();
        }
    },

    UbezpieczenieWypadkowePracodawca {
        @Override public <T> T accept(PayslipFunctionVisitor<T> visitor) {
            return visitor.visitUbezpieczenieWypadkowePracodawca();
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

    FunduszFPFS {
        @Override public <T> T accept( PayslipFunctionVisitor<T> visitor) {
            return visitor.visitFunduszFPFS();
        }
    },

    FunduszFGSP {
        @Override public <T> T accept( PayslipFunctionVisitor<T> visitor) {
            return visitor.visitFunduszFGSP();
        }
    },

    KwotaZaliczki {
        @Override public <T> T accept( PayslipFunctionVisitor<T> visitor ) {
            return visitor.visitKwotaZaliczki();
        }
    },
    WynagrodzenieNetto {
        @Override public <T> T accept( PayslipFunctionVisitor<T> visitor ) {
            return visitor.visitWynagrodzenieNetto();
        }
    };

    public abstract <T> T accept( PayslipFunctionVisitor<T> visitor);

    public interface PayslipFunctionVisitor<T> {
        T visitUbezpieczenieEmerytalneZatrudniony();
        T visitUbezpieczenieEmerytalnePracodawca();
        T visitUbezpieczenieRentoweZatrudniony();
        T visitUbezpieczenieRentowePracodawca();
        T visitUbezpieczenieChoroboweZatrudniony();
        T visitUbezpieczenieWypadkowePracodawca();
        T visitFunduszFPFS();
        T visitFunduszFGSP();
        T visitWynagrodzenieBrutto();
        T visitUbezpieczenieZdrowotne();
        T visitKwotaZaliczki();
        T visitWynagrodzenieNetto();

    }
}
