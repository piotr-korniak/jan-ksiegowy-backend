package pl.janksiegowy.backend.accounting.decree;

import lombok.AllArgsConstructor;
import pl.janksiegowy.backend.accounting.account.Account;
import pl.janksiegowy.backend.accounting.account.AccountFacade;
import pl.janksiegowy.backend.accounting.account.AccountRepository;
import pl.janksiegowy.backend.accounting.account.dto.AccountDto;
import pl.janksiegowy.backend.accounting.decree.dto.DecreeLineDto;
import pl.janksiegowy.backend.accounting.account.AccountPage.AccountPageVisitor;

@AllArgsConstructor
public class DecreeLineFactory implements AccountPageVisitor<DecreeLine>{

    private final AccountRepository accounts;
    private final AccountFacade accountFacade;

    public DecreeLine from( DecreeLineDto source ) {
        return create( source);
    }

    private DecreeLine create( DecreeLineDto source) {

        Account account= accounts.findByNumber( source.getAccount().getNumber())
            .orElseGet( ()-> accounts.findByNumber( source.getAccount().getParent())
                    .map( parent-> accountFacade.save( AccountDto.create()
                            .number( source.getAccount().getNumber())
                            .name( source.getAccount().getName())
                            .parent( parent.getNumber())
                            .type( parent.getType()))
                            .setParent( parent))
                    .orElseThrow());

        return source.getPage().accept( this)
                .setValue( source.getValue())
                .setAccount( account);
    }

    @Override public DecreeLine visitDtPage() {
        return new DecreeDtLine();
    }

    @Override public DecreeLine visitCtPage() {
        return new DecreeCtLine();
    }
}
