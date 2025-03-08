package pl.janksiegowy.backend.accounting.decree;

import pl.janksiegowy.backend.accounting.account.dto.AccountDto;
import pl.janksiegowy.backend.accounting.template.TemplateLine;
import pl.janksiegowy.backend.entity.Entity;
import pl.janksiegowy.backend.entity.EntityType;
import pl.janksiegowy.backend.register.RegisterType;
import pl.janksiegowy.backend.register.payment.PaymentRegister;
import pl.janksiegowy.backend.register.payment.PaymentRegisterType;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public class DecreeFacadeTools {

    public static Optional<AccountDto> expandEntityAccount( String account, Entity entity ) {
        try {
            if( EntityType.valueOf( account.replaceAll("[^A-Z]", ""))!= entity.getType())
                return Optional.empty();
            return Optional.of( AccountDto.create()
                    .name( entity.getName())
                    .parent( account)
                    .number( account.replaceAll( "\\[[A-Z]]+", entity.getAccountNumber())));
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }

    public static Optional<AccountDto> expandPaymentRegisterAccount( String account, PaymentRegister register ) {
        try {
            if( RegisterType.valueOf( account.replaceAll("[^A-Z]", ""))!= register.getType())
                return Optional.empty();
            return Optional.of( AccountDto.create()
                    .name( register.getName())
                    .parent( account)
                    .number( account.replaceAll( "\\[[A-Z]]+", register.getLedgerAccountNumber())));
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }

}
