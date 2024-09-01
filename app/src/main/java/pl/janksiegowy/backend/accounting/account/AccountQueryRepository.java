package pl.janksiegowy.backend.accounting.account;

import pl.janksiegowy.backend.accounting.account.dto.AccountDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AccountQueryRepository {
    boolean existsByNumber( String number);

    Optional<AccountDto> findByNumber( String number);
    List<AccountDto> findByParentId( UUID parentId);

    default List<AccountDto> findAllAnalyticalBlanceAccountsByType() {
        return findAllAnalyticalAccountsByType( BalanceAccount.class);
    }

    List<AccountDto> findAllAnalyticalAccountsByType( Class<? extends Account> type);
}
