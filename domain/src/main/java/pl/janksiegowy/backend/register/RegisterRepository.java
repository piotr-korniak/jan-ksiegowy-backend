package pl.janksiegowy.backend.register;

import pl.janksiegowy.backend.register.accounting.AccountingRegister;
import pl.janksiegowy.backend.register.invoice.PurchaseRegister;
import pl.janksiegowy.backend.register.invoice.SalesRegister;

import java.util.Optional;

public interface RegisterRepository {

    Optional<Register> findByCode( String code);
    Optional<Register> findByTypeAndCode( RegisterType type, String code);

    default Optional<AccountingRegister> findAccountRegisterByCode(String code) {
        return findByTypeAndCode( RegisterType.A, code).map( r-> (AccountingRegister) r);
    }

    default Optional<SalesRegister> findSalesRegisterByCode( String code) {
        return findByTypeAndCode( RegisterType.S, code).map( r-> (SalesRegister) r);
    }

    default Optional<PurchaseRegister> findPurchaseRegisterByCode( String code) {
        return findByTypeAndCode( RegisterType.P, code).map( r-> (PurchaseRegister) r);
    }

    Register save( Register register);
}
