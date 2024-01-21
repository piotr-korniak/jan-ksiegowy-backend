package pl.janksiegowy.backend.register;

import pl.janksiegowy.backend.register.invoice.PurchaseRegister;
import pl.janksiegowy.backend.register.invoice.SalesRegister;

import java.util.Optional;

public interface RegisterRepository {

    Optional<SalesRegister> findVatSalesRegisterByCode( String code);
    Optional<PurchaseRegister> findVatPurchaseRegisterByCode( String code);
    public Register save( Register register);


}
