package pl.janksiegowy.backend.register;

import pl.janksiegowy.backend.register.dto.VatRegisterDto;
import pl.janksiegowy.backend.register.invoice.PurchaseRegister;
import pl.janksiegowy.backend.register.RegisterType.RegisterTypeVisitor;
import pl.janksiegowy.backend.register.invoice.SalesRegister;

public class RegisterFactory {
    public Register from( VatRegisterDto source) {
        return update( create( source), source);
    }

    public Register update( VatRegisterDto source) {
        return update( create( source)
                .setRegisterId( source.getRegisterId()), source);
    }

    public Register update( Register register, VatRegisterDto source) {
        return register
                .setCode( source.getCode())
                .setName( source.getName());
    }

    private Register create( VatRegisterDto source) {
        return source.getType().accept( new RegisterTypeVisitor<Register>() {
            @Override public Register visitSalesVatRegister() {
                return new SalesRegister()
                        .setKind( source.getKind());
            }

            @Override public Register visitPurchaseVatRegister() {
                return new PurchaseRegister()
                        .setKind( source.getKind());
            }
        });
    }


}
