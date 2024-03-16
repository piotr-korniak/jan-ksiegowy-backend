package pl.janksiegowy.backend.register.accounting;

import pl.janksiegowy.backend.register.Register;
import pl.janksiegowy.backend.register.dto.RegisterDto;

public class AccountingRegisterFactory {
    public AccountingRegister from( RegisterDto source) {
        return update( source, new AccountingRegister());
    }

    public AccountingRegister update( RegisterDto source) {
        return update( source, new AccountingRegister()
                .setRegisterId( source.getRegisterId()));
    }

    public AccountingRegister update( RegisterDto source, Register register) {
        return (AccountingRegister) register
                .setCode( source.getCode())
                .setName( source.getName());
    }

}
