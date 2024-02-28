package pl.janksiegowy.backend.register.invoice;

import pl.janksiegowy.backend.register.dto.RegisterDto;
import pl.janksiegowy.backend.register.invoice.InvoiceRegisterType.InvoiceRegisterTypeVisitor;

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
        return InvoiceRegisterType.valueOf( source.getType())
                .accept( new InvoiceRegisterTypeVisitor<InvoiceRegister>() {
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
