package pl.janksiegowy.backend.accounting.template;

import pl.janksiegowy.backend.accounting.account.AccountType;

public enum TemplateType {
    B {
        @Override public <T> T accept( TemplateTypeVisitor<T> visitor ) {
            return visitor.visitBankTemplate();
        }
    },
    C {
        @Override public <T> T accept( TemplateTypeVisitor<T> visitor ) {
            return visitor.visitCashTemplate();
        }
    },
    S {
        @Override public <T> T accept( TemplateTypeVisitor<T> visitor ) {
            return visitor.visitSalesInvoiceTemplate();
        }
    },
    P {
        @Override public <T> T accept( TemplateTypeVisitor<T> visitor ) {
            return visitor.visitPeriodInvoiceTemplate();
        }
    };

    public abstract <T> T accept( TemplateTypeVisitor<T> visitor);

    public interface TemplateTypeVisitor<T> {
        T visitBankTemplate();
        T visitCashTemplate();
        T visitSalesInvoiceTemplate();
        T visitPeriodInvoiceTemplate();
    }
}
