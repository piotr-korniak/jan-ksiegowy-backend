package pl.janksiegowy.backend.accounting.account;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import pl.janksiegowy.backend.accounting.account.dto.AccountDto;
import pl.janksiegowy.backend.shared.MigrationService;
@Log4j2

@AllArgsConstructor
public class AccountFacade {

    private final AccountFactory account;
    private final AccountRepository accountRepository;
    private final AccountQueryRepository accounts;
    private final MigrationService migrationService;

    public Account save( AccountDto source) {
        return accountRepository.save( account.from( source));
    }

    public String migrate() {
        int[] counters= { 0, 0};

        migrationService.loadAccounts()
                .forEach( accountDto-> {
                    counters[0]++;

                    if( !accounts.existsByNumber( accountDto.getNumber())) {
                        save( accountDto);
                        counters[1]++;
                    }
                });

        log.warn( "Accounts migration complete!");
        return String.format( "%-50s %13s", "Accounts migration complete, added: ", counters[1]+ "/"+ counters[0]);
    }
}
