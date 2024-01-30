package pl.janksiegowy.backend.statement;

public enum JpkVat {

    JPK_V7K_2_v1_0e {
        @Override public <T> T accept( Jpk_Vat7kVisitor<T> visitor ) {
            return visitor.visit_JPK_V7K_2_v1_0e();
        }
    };

    public abstract <T> T accept( Jpk_Vat7kVisitor<T> visitor);

    public interface Jpk_Vat7kVisitor<T> {
        T visit_JPK_V7K_2_v1_0e();

    }
}
