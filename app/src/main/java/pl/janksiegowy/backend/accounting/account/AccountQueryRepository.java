package pl.janksiegowy.backend.accounting.account;

public interface AccountQueryRepository {
    boolean existsByNumber( String number);
}
