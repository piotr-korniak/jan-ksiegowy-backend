package pl.janksiegowy.backend.accounting.template;

import java.math.BigDecimal;

public enum PaymentFunction {

    WplataNaleznosci {
        @Override public <T> T accept( PaymentFunctionVisitor<T> visitor ) {
            return visitor.visitWplataNaleznosci();
        }
    },
    SplataZobowiazania {
        @Override public <T> T accept( PaymentFunctionVisitor<T> visitor ) {
            return visitor.visitSplataZobowiazania();
        }
    },
    WartoscRozrachowania {
        @Override public <T> T accept( PaymentFunctionVisitor<T> visitor) {
            return visitor.visitWartoscRozrachowania();
        }
    },
    WartoscRozrachowaniaPublicznego {
        @Override public <T> T accept( PaymentFunctionVisitor<T> visitor) {
            return visitor.visitWartoscRozrachowaniaPublicznego();
        }
    },
    WartoscRozrachowaniaPoTerminie {
        @Override public <T> T accept( PaymentFunctionVisitor<T> visitor) {
            return visitor.visitWartoscRozrachowaniaPoTerminie();
        }
    },
    WplataNoty {
        @Override public <T> T accept( PaymentFunctionVisitor<T> visitor ) {
            return visitor.visitWplataNoty();
        }
    },
    SplataNoty {
        @Override public <T> T accept( PaymentFunctionVisitor<T> visitor ) {
            return visitor.visitSplataNoty();
        }
    },
    SplataVat {
        @Override public <T> T accept( PaymentFunctionVisitor<T> visitor) {
            return visitor.visitSplataVat();
        }
    },
    SplataNKUP {
        @Override public <T> T accept(PaymentFunctionVisitor<T> visitor) {
            return visitor.visitSplataNKUP();
        }
    },
    WynagrodzenieNetto {
        @Override public <T> T accept( PaymentFunctionVisitor<T> visitor ) {
            return visitor.visitWynagrodzenieNetto();
        }
    }
    ;

    public abstract <T> T accept( PaymentFunctionVisitor<T> visitor);

    public interface PaymentFunctionVisitor<T> {
        T visitWplataNaleznosci();
        T visitSplataZobowiazania();
        T visitWplataNoty();
        T visitSplataNoty();
        T visitSplataVat();
        T visitSplataNKUP();
        T visitWartoscRozrachowania();
        T visitWartoscRozrachowaniaPublicznego();
        T visitWartoscRozrachowaniaPoTerminie();
        T visitWynagrodzenieNetto();
    }
}
