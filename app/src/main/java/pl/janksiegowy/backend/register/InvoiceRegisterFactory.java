package pl.janksiegowy.backend.register;

import pl.janksiegowy.backend.register.dto.RegisterDto;
import pl.janksiegowy.backend.register.invoice.InvoiceRegister;
import pl.janksiegowy.backend.register.invoice.InvoiceRegisterKind;
import pl.janksiegowy.backend.register.invoice.PurchaseRegister;
import pl.janksiegowy.backend.register.invoice.InvoiceRegisterType.InvoiceRegisterTypeVisitor;
import pl.janksiegowy.backend.register.invoice.SalesRegister;

public class InvoiceRegisterFactory {
    public InvoiceRegister from( RegisterDto source) {
        return update( create( source), source);
    }

    public InvoiceRegister update( RegisterDto source) {
        return update( (InvoiceRegister) create( source)
                .setRegisterId( source.getRegisterId()), source);
    }

    public InvoiceRegister update( InvoiceRegister register, RegisterDto source) {
        return (InvoiceRegister) register
                .setCode( source.getCode())
                .setName( source.getName());
    }

    private InvoiceRegister create( RegisterDto source) {
        return source.getType().accept( new InvoiceRegisterTypeVisitor<InvoiceRegister>() {
            @Override public InvoiceRegister visitSalesRegister() {
                return new SalesRegister()
                        .setKind( InvoiceRegisterKind.valueOf( source.getKind()));
            }
            @Override public InvoiceRegister visitPurchaseRegister() {
                return new PurchaseRegister()
                        .setKind( InvoiceRegisterKind.valueOf( source.getKind()));
            }
        });
    }

}
