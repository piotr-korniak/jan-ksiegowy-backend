package pl.janksiegowy.backend.register;

import pl.janksiegowy.backend.register.Register;
import pl.janksiegowy.backend.register.vat.VatPurchaseRegister;
import pl.janksiegowy.backend.register.vat.VatSalesRegister;

import java.util.Optional;

public interface RegisterRepository {

    Optional<VatSalesRegister> findVatSalesRegisterByCode( String code);
    Optional<VatPurchaseRegister> findVatPurchaseRegisterByCode( String code);
    public Register save( Register register);


}
