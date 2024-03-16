package pl.janksiegowy.backend.register.invoice;

import lombok.AllArgsConstructor;
import pl.janksiegowy.backend.register.Register;
import pl.janksiegowy.backend.register.dto.RegisterDto;
import pl.janksiegowy.backend.register.invoice.InvoiceRegisterType.InvoiceRegisterTypeVisitor;
import pl.janksiegowy.backend.shared.numerator.NumeratorCode;
import pl.janksiegowy.backend.shared.numerator.NumeratorFacade;

import java.util.Optional;

@AllArgsConstructor
public class InvoiceRegisterFactory implements InvoiceRegisterTypeVisitor<InvoiceRegister>{

    private final NumeratorFacade numerators;

    public InvoiceRegister from( RegisterDto source) {
        return update( source, InvoiceRegisterType.valueOf( source.getType()).accept( this)
                .setAccountNumber( numerators.increment( NumeratorCode.RE, source.getType())));
    }

    public InvoiceRegister update( RegisterDto source) {
        return update( source, InvoiceRegisterType.valueOf( source.getType()).accept( this)
                .setRegisterId( source.getRegisterId())
                .setAccountNumber( source.getAccountNumber()));
    }

    public InvoiceRegister update( RegisterDto source, Register register) {
        return ((InvoiceRegister) register
                .setCode( source.getCode())
                .setName( source.getName()))
                .setKind( InvoiceRegisterKind.valueOf( source.getKind()));
    }

    @Override public InvoiceRegister visitSalesRegister() {
        return new SalesRegister();
    }
    @Override public InvoiceRegister visitPurchaseRegister() {
        return new PurchaseRegister();
    }
}
