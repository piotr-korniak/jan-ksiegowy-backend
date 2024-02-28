package pl.janksiegowy.backend.register.accounting;

import pl.janksiegowy.backend.register.dto.RegisterDto;
import pl.janksiegowy.backend.register.accounting.AccountingRegisterType.AccountingRegisterTypeVisitor;

public class AccountingRegisterFactory {
    public AccountingRegister from( RegisterDto source) {
        return update( create( source), source);
    }

    public AccountingRegister update( RegisterDto source) {
        return update( (AccountingRegister) create( source)
                .setRegisterId( source.getRegisterId()), source);
    }

    public AccountingRegister update( AccountingRegister register, RegisterDto source) {
        return (AccountingRegister) register
                .setCode( source.getCode())
                .setName( source.getName());
    }

    private AccountingRegister create( RegisterDto source) {
        return AccountingRegisterType.valueOf( source.getType())
                .accept( new AccountingRegisterTypeVisitor<AccountingRegister>() {
            @Override public AccountingRegister visitSalesInvoiceRegister() {
                return new SalesAccountingRegister();
            }
            @Override public AccountingRegister visitPurchaseInvoiceRegister() {
                return null;
            }
            @Override public AccountingRegister visitPaymentRegister() {
                return new PaymentAccountingRegister();
            }
            @Override public AccountingRegister visitPayrollRegister() {
                return null;
            }
            @Override public AccountingRegister visitAssetRegister() {
                return null;
            }
            @Override public AccountingRegister visitShareholdingRegister() {
                return null;
            }
            @Override public AccountingRegister visitStatementRegister() {
                return null;
            }
            @Override public AccountingRegister visitDecreeRegister() {
                return null;
            }
        });

    }
}
