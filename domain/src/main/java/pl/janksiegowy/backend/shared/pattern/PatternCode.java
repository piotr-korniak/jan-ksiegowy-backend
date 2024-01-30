package pl.janksiegowy.backend.shared.pattern;

public enum PatternCode {

    FA {
        @Override public <T> T accept( PatternCodeVisitor<T> visitor) {
            return visitor.visit_FA();
        }
    },
    JPK_V7K {
        @Override public <T> T accept( PatternCodeVisitor<T> visitor) {
            return visitor.visit_JPK_V7K();
        }
    };

    public abstract <T> T accept( PatternCodeVisitor<T> visitor);

    public interface PatternCodeVisitor<T> {
        T visit_JPK_V7K();
        T visit_FA();
    }
}
