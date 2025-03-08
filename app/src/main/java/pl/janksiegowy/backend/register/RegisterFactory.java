package pl.janksiegowy.backend.register;

import lombok.AllArgsConstructor;
import pl.janksiegowy.backend.register.accounting.AccountingRegister;
import pl.janksiegowy.backend.register.dto.RegisterDto;
import pl.janksiegowy.backend.register.RegisterType.RegisterTypeVisitor;
import pl.janksiegowy.backend.register.invoice.PurchaseRegister;
import pl.janksiegowy.backend.register.invoice.SalesRegister;
import pl.janksiegowy.backend.register.payment.BankAccount;
import pl.janksiegowy.backend.register.payment.CashDesk;
import pl.janksiegowy.backend.shared.numerator.NumeratorCode;
import pl.janksiegowy.backend.shared.numerator.NumeratorFacade;

import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
public class RegisterFactory implements RegisterTypeVisitor<Register, RegisterDto> {
    private final NumeratorFacade numerators;

    public Register from( RegisterDto source) {

        return source.getType().accept( this, source)
                .setRegisterId( Optional.ofNullable( source.getRegisterId()).orElseGet( UUID::randomUUID))
                .setLedgerAccountNumber( Optional.ofNullable( source.getLedgerAccountNumber())
                        .orElseGet(()-> numerators.increment( NumeratorCode.RE, source.getType().name())))
                .setCode( source.getCode())
                .setName( source.getName());
    }

    @Override public Register visitAccountingRegister( RegisterDto source) {
        return new AccountingRegister();
    }

    @Override public Register visitBankAccount( RegisterDto source) {
        return new BankAccount()
                .setBankNumber( source.getBankAccountNumber())
                .setCurrency( source.getCurrency());
    }

    @Override public Register visitCashDesk( RegisterDto source) {
        return new CashDesk();
    }

    @Override public Register visitSalesRegister( RegisterDto source) {
        return new SalesRegister().setKind( source.getKind());
    }

    @Override public Register visitPurchaseRegister( RegisterDto source) {
        return new PurchaseRegister().setKind( source.getKind());
    }
}
