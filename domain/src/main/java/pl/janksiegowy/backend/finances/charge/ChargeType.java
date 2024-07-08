package pl.janksiegowy.backend.finances.charge;

public enum ChargeType {
    /** Fee */
    F { @Override public <T> T accept( ChargeTypeVisitor<T> visitor) {
        return visitor.visitFeeCharge();
    }},

    /** Levy */
    L { @Override public <T> T accept( ChargeTypeVisitor<T> visitor) {
        return visitor.visitCharge();
    }};

    public abstract <T> T accept( ChargeTypeVisitor<T> visitor);

    public interface ChargeTypeVisitor<T> {
        T visitFeeCharge();
        T visitCharge();
    }
}
