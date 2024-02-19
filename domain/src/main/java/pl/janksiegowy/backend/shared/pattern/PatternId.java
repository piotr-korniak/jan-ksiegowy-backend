package pl.janksiegowy.backend.shared.pattern;

import pl.janksiegowy.backend.shared.pattern.PatternCode.PatternCodeVisitor;
public enum PatternId {

    JPK_V7K_2_v1_0e {
        @Override public <T> T accept( PatternIdVisitor<T> visitor ) {
            return visitor.visit_JPK_V7K_2_v1_0e();
        }

        @Override public <T> T accept( PatternCodeVisitor<T> visitor ) {
            return PatternCode.JPK_V7K.accept( visitor);
        }
    },

    FA_2_v1_0e {
        @Override public <T> T accept( PatternIdVisitor<T> visitor ) {
            return visitor.visit_FA_2_v1_0e();
        }

        @Override public <T> T accept( PatternCodeVisitor<T> visitor ) {
            return visitor.visit_FA();
        }
    };

    public abstract <T> T accept( PatternIdVisitor<T> visitor);
    public abstract <T> T accept( PatternCodeVisitor<T> visitor);

    public interface PatternIdVisitor<T> {
        T visit_JPK_V7K_2_v1_0e();
        T visit_FA_2_v1_0e();
    }
}
