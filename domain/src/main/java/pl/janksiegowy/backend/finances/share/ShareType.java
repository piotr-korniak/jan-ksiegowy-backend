package pl.janksiegowy.backend.finances.share;

public enum ShareType {
    A {
        @Override public <T> T accept( ShareTypeVisitor<T> visitor) {
            return visitor.visitAcquireShare();
        }
    },
    D {
        @Override public <T> T accept( ShareTypeVisitor<T> visitor) {
            return visitor.visitDisposedShare();
        }
    };

    public abstract <T> T accept( ShareTypeVisitor<T> visitor);

    public interface ShareTypeVisitor<T> {
        T visitAcquireShare();
        T visitDisposedShare();
    }
}
