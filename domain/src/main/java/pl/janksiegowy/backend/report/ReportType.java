package pl.janksiegowy.backend.report;

import pl.janksiegowy.backend.entity.EntityType;

public enum ReportType {
    B { // Balance
        @Override public <T> T accept( ReportTypeVisitor<T> visitor) {
            return visitor.visitBalance();
        }
    },
    C { // CIT
        @Override public <T> T accept( ReportTypeVisitor<T> visitor) {
            return visitor.visitCIT();
        }
    },
    P {
        @Override public <T> T accept( ReportTypeVisitor<T> visitor) {
            return visitor.visitProfitAndLoss();
        }
    };  // Profit And Loss

    public abstract <T> T accept( ReportTypeVisitor<T> visitor);

    interface ReportTypeVisitor<T> {
        T visitBalance();
        T visitCIT();
        T visitProfitAndLoss();
    }
}
