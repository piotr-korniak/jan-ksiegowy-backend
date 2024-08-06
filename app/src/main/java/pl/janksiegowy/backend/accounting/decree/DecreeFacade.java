package pl.janksiegowy.backend.accounting.decree;

import lombok.AllArgsConstructor;
import pl.janksiegowy.backend.accounting.decree.dto.DecreeDto;
import pl.janksiegowy.backend.finances.document.Document;
import pl.janksiegowy.backend.finances.charge.Charge;
import pl.janksiegowy.backend.register.accounting.AccountingRegister;
import pl.janksiegowy.backend.register.accounting.AccountingRegisterFactory;
import pl.janksiegowy.backend.register.accounting.AccountingRegisterRepository;
import pl.janksiegowy.backend.register.dto.RegisterDto;
import pl.janksiegowy.backend.statement.PayableStatement;

import java.util.Optional;

@AllArgsConstructor
public class DecreeFacade {

    private final AccountingRegisterFactory register;
    private final AccountingRegisterRepository registers;
    private final DecreeFactory decree;
    private final DecreeRepository decrees;

    public Decree book( Document document) {
        return save( decree.to( document));
    }

    public void book( PayableStatement statement) {
        save( decree.to( statement));
    }

    public Decree save( DecreeDto source) {
        return decrees.save( decree.from( source));
    }

    public AccountingRegister save( RegisterDto source) {
        return registers.save( Optional.ofNullable( source.getRegisterId())
                .map( uuid -> register.update( source))
                .orElse( register.from( source)));
    }



}
