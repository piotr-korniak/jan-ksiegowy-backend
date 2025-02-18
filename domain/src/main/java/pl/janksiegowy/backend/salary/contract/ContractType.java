package pl.janksiegowy.backend.salary.contract;

public enum ContractType {
    /** Employment Contract, Umowa o pracę */
    E {
        @Override public <T> T accept( ContractTypeVisitor<T> visitor ) {
            return visitor.visitEmploymentContract();
        }
    },
    /** Mandate Contract, Umowa zlecenie */
    M {
        @Override public <T> T accept( ContractTypeVisitor<T> visitor) {
            return visitor.visitMandateContract();
        }
    },
    /** Work Contract, Umowa o dzieło */
    W {
        @Override public <T> T accept( ContractTypeVisitor<T> visitor ) {
            return visitor.visitWorkContract();
        }
    };

    public abstract <T> T accept( ContractTypeVisitor<T> visitor);

    public interface ContractTypeVisitor<T> {
        T visitEmploymentContract();
        T visitMandateContract();
        T visitWorkContract();
    }
}
