package pl.janksiegowy.backend.accounting.decree;

public enum DecreeType {
    B { // Booking decree
        @Override public <T> T accept( DecreeTypeVisitor<T> visitor) {
            return visitor.visitBookingDecree();
        }
    },
    D { // Settlement decree
        @Override public <T> T accept( DecreeTypeVisitor<T> visitor) {
            return visitor.visitDocumentDecree();
        }
    };

    public abstract <T> T accept( DecreeTypeVisitor<T> visitor);

    public interface DecreeTypeVisitor<T> {
        T visitBookingDecree();
        T visitDocumentDecree();

    }
}
