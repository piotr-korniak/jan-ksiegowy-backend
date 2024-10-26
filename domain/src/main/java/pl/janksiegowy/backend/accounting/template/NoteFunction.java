package pl.janksiegowy.backend.accounting.template;

public enum NoteFunction {
    Kwota {
        @Override public <T> T accept( NoteFunctionVisitor<T> visitor) {
            return visitor.visitKwota();
        }
    },
    KwotaKUP {
        @Override public <T> T accept( NoteFunctionVisitor<T> visitor) {
            return visitor.visitKwotaKUP();
        }
    },
    KwotaNUP {
        @Override public <T> T accept( NoteFunctionVisitor<T> visitor) {
            return visitor.visitKwotaNUP();
        }
    };

    public abstract <T> T accept( NoteFunctionVisitor<T> visitor);

    public interface NoteFunctionVisitor<T> {
        T visitKwota();
        T visitKwotaKUP();
        T visitKwotaNUP();
    }
}
