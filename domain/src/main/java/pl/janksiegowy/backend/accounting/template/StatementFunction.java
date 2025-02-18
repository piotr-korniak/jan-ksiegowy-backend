package pl.janksiegowy.backend.accounting.template;

public enum StatementFunction {
    PodatekNalezny {
        @Override public <T> T accept( StatementFunctionVisitor<T> visitor ) {
            return visitor.visitPodatekNalezny();
        }
    },
    PodatekNaliczony {
        @Override public <T> T accept( StatementFunctionVisitor<T> visitor ) {
            return visitor.visitPodatekNaliczony();
        }
    },
    DoPrzeniesienia {
        @Override public <T> T accept( StatementFunctionVisitor<T> visitor) {
            return visitor.visitDoPrzeniesienia();
        }
    },
    ZPrzeniesienia {
        @Override public <T> T accept( StatementFunctionVisitor<T> visitor) {
            return visitor.visitZPrzeniesienia();
        }
    },
    KorektaNaleznegoPlus {
        @Override public <T> T accept( StatementFunctionVisitor<T> visitor ) {
            return visitor.visitKorektaNaleznegoPlus();
        }
    },
    KorektaNaleznegoMinus {
        @Override public <T> T accept( StatementFunctionVisitor<T> visitor ) {
            return visitor.visitKorektaNaleznegoMinus();
        }
    },
    KorektaNaliczonegoPlus {
        @Override public <T> T accept( StatementFunctionVisitor<T> visitor ) {
            return visitor.visitKorektaNaliczonegoPlus();
        }
    },
    KorektaNaliczonegoMinus {
        @Override public <T> T accept( StatementFunctionVisitor<T> visitor ) {
            return visitor.visitKorektaNaliczonegoMinus();
        }
    },

    UbezpieczenieEmerytalne {
        @Override public <T> T accept(StatementFunctionVisitor<T> visitor) {
            return visitor.visitUbezpieczenieEmerytalne();
        }
    },

    UbezpieczenieRentowe {
        @Override public <T> T accept(StatementFunctionVisitor<T> visitor) {
            return visitor.visitUbezpieczenieRentowe();
        }
    },

    UbezpieczenieChorobowe {
        @Override public <T> T accept(StatementFunctionVisitor<T> visitor) {
            return visitor.visitUbezpieczenieChorobowe();
        }
    },

    UbezpieczenieWypadkowe {
        @Override public <T> T accept(StatementFunctionVisitor<T> visitor) {
            return visitor.visitUbezpieczenieWypadkowe();
        }
    },

    UbezpieczenieZdrowotne {
        @Override public <T> T accept(StatementFunctionVisitor<T> visitor) {
            return visitor.visitUbezpieczenieZdrowotne();
        }
    },

    FunduszFGSP {
        @Override public <T> T accept( StatementFunctionVisitor<T> visitor) {
            return visitor.visitFunduszFGSP();
        }
    },

    FunduszFPFS {
        @Override public <T> T accept( StatementFunctionVisitor<T> visitor) {
            return visitor.visitFunduszFPFS();
        }
    },

    Zobowiazanie {
        @Override public <T> T accept( StatementFunctionVisitor<T> visitor ) {
            return visitor.visitZobowiazanie();
        }
    },
    ZaliczkaCIT {
        @Override public <T> T accept( StatementFunctionVisitor<T> visitor) {
            return visitor.visitZaliczkaCIT();
        }
    };

    public abstract <T> T accept( StatementFunctionVisitor<T> visitor);

    public interface StatementFunctionVisitor<T> {
        T visitPodatekNalezny();
        T visitPodatekNaliczony();
        T visitDoPrzeniesienia();
        T visitZPrzeniesienia();
        T visitKorektaNaleznegoPlus();
        T visitKorektaNaleznegoMinus();
        T visitKorektaNaliczonegoPlus();
        T visitKorektaNaliczonegoMinus();
        T visitUbezpieczenieEmerytalne();
        T visitUbezpieczenieRentowe();
        T visitUbezpieczenieChorobowe();
        T visitUbezpieczenieWypadkowe();
        T visitUbezpieczenieZdrowotne();
        T visitFunduszFGSP();
        T visitFunduszFPFS();
        T visitZobowiazanie();
        T visitZaliczkaCIT();
    }
}
