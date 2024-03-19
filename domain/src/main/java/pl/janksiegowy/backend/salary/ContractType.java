package pl.janksiegowy.backend.salary;

public enum ContractType {
    E {
        @Override public <T> T accept( ContractTypeVisitor<T> visitor ) {
            return visitor.visitEmploymentContract();
        }
    },
    S {
        @Override public <T> T accept( ContractTypeVisitor<T> visitor ) {
            return visitor.visitServicesContract();
        }
    };

    public abstract <T> T accept( ContractTypeVisitor<T> visitor);

    public interface ContractTypeVisitor<T> {
        T visitEmploymentContract();
        T visitServicesContract();
    }
}
