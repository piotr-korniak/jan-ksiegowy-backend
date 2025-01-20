package pl.janksiegowy.backend.shared.pattern;

public enum PatternId {

    VAT_7_17_1_0e {
        @Override
        public <T> T accept(PatternJpkVisitor<T> visitor) {
            return null;
        }

        @Override
        public <T> T accept(PatternCitVisitor<T> visitor) {
            return null;
        }
    },

    JPK_V7K_2_1_0e {
        @Override public <T> T accept( PatternJpkVisitor<T> visitor) {
            return visitor.visit_JPK_V7K_2_v1_0e();
        }
        @Override public <T> T accept( PatternCitVisitor<T> visitor) {
            return null;
        }
    },
    FA_2_v1_0e {
        @Override public <T> T accept( PatternJpkVisitor<T> visitor) {
            return null;
        }
        @Override public <T> T accept( PatternCitVisitor<T> visitor) {
            return null;
        }
    },
    CIT_8_25_v1_0e {
        @Override
        public <T> T accept(PatternJpkVisitor<T> visitor) {
            return null;
        }

        @Override
        public <T> T accept(PatternCitVisitor<T> visitor) {
            return null;
        }
    },
    CIT_8_33_v2_0e {
        @Override public <T> T accept( PatternJpkVisitor<T> visitor) {
            return null;
        }
        @Override public <T> T accept( PatternCitVisitor<T> visitor) {
            return visitor.visit_CIT_8_33_v2_0e();
        }
    },
    PIT_4R_6_v1_0e {
        @Override
        public <T> T accept(PatternJpkVisitor<T> visitor) {
            return null;
        }

        @Override
        public <T> T accept(PatternCitVisitor<T> visitor) {
            return null;
        }
    };

    public abstract <T> T accept( PatternJpkVisitor<T> visitor);
    public abstract <T> T accept( PatternCitVisitor<T> visitor);

    public interface PatternJpkVisitor<T> {
        T visit_JPK_V7K_2_v1_0e();
    }

    public interface PatternCitVisitor<T> {
        T visit_CIT_8_33_v2_0e();
    }
}
