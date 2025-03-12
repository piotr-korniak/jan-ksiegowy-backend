package pl.janksiegowy.backend.register;

import pl.janksiegowy.backend.register.accounting.AccountingRegister;
import pl.janksiegowy.backend.register.invoice.PurchaseRegister;
import pl.janksiegowy.backend.register.invoice.SalesRegister;

import java.util.Optional;
import java.util.UUID;

public interface RegisterRepository {

    Optional<Register> findByCode( String code);
    Optional<Register> findByTypeAndRegisterId( RegisterType type, UUID registerId);

    default Optional<SalesRegister> findSalesRegisterByRegisterId(UUID registerId) {
        return findByTypeAndRegisterId( RegisterType.S, registerId).map( r-> (SalesRegister) r);
    }

    default Optional<AccountingRegister> findAccountRegisterByRegisterId( UUID registerId) {
        return findByTypeAndRegisterId( RegisterType.A, registerId).map( r-> (AccountingRegister) r);
    };

    default Optional<PurchaseRegister> findPurchaseRegisterById( UUID registerId) {
        return findByTypeAndRegisterId( RegisterType.P, registerId).map( r-> (PurchaseRegister) r);
    }

    Register save( Register register);

}
