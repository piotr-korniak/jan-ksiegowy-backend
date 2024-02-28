package pl.janksiegowy.backend.accounting.decree;

import pl.janksiegowy.backend.accounting.decree.dto.DecreeLineDto;
import pl.janksiegowy.backend.accounting.account.AccountPage.AccountPageVisitor;

public class DecreeLineFactory implements AccountPageVisitor<DecreeLine>{
    public DecreeLine from( DecreeLineDto source ) {
        return create( source);
    }

    private DecreeLine create( DecreeLineDto source) {
        return source.getPage().accept( this);
    }

    @Override public DecreeLine visitDtPage() {
        return new DecreeDtLine();
    }

    @Override public DecreeLine visitCtPage() {
        return new DecreeCtLine();
    }
}
