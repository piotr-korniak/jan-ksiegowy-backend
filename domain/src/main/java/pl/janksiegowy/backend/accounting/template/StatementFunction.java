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
        T visitZobowiazanie();
        T visitZaliczkaCIT();
    }
}
