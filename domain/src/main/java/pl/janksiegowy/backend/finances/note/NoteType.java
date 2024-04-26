package pl.janksiegowy.backend.finances.note;


public enum NoteType {

    /** Issued Note */
    I { @Override public <T> T accept( NoteTypeVisitor<T> visitor) {
        return visitor.visitIssuedNote();
    }},

    /** Received Note */
    N { @Override public <T> T accept( NoteTypeVisitor<T> visitor) {
        return visitor.visitReceiveNote();
    }};

    public abstract <T> T accept( NoteTypeVisitor<T> visitor);

    public interface NoteTypeVisitor<T> {
        T visitIssuedNote();
        T visitReceiveNote();
    }
}
