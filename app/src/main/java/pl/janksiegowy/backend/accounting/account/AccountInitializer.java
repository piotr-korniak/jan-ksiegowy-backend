package pl.janksiegowy.backend.accounting.account;

import lombok.AllArgsConstructor;
import pl.janksiegowy.backend.accounting.account.dto.AccountDto;

import java.util.List;

@AllArgsConstructor
public class AccountInitializer {

    private final AccountFacade account;
    private final AccountQueryRepository accounts;

    public void init( List<AccountDto> initialAccount) {

        initialAccount.forEach( accountDto-> {
            if( !accounts.existsByNumber( accountDto.getNumber())) {
                account.save( accountDto);
            }
        });
    }
}
