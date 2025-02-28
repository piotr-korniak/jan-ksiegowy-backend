package pl.janksiegowy.backend.database;

import pl.janksiegowy.backend.accounting.account.AccountSide;
import pl.janksiegowy.backend.accounting.account.dto.AccountDto;
import pl.janksiegowy.backend.accounting.account.AccountType;
import pl.janksiegowy.backend.accounting.template.*;
import pl.janksiegowy.backend.accounting.template.dto.TemplateDto;
import pl.janksiegowy.backend.accounting.template.dto.TemplateLineDto;
import pl.janksiegowy.backend.accounting.template.dto.TemplateMap;
import pl.janksiegowy.backend.entity.EntityType;
import pl.janksiegowy.backend.finances.settlement.SettlementType;
import pl.janksiegowy.backend.register.accounting.AccountingRegisterType;
import pl.janksiegowy.backend.register.dto.RegisterDto;
import pl.janksiegowy.backend.shared.numerator.NumeratorCode;
import pl.janksiegowy.backend.shared.numerator.NumeratorType;
import pl.janksiegowy.backend.shared.numerator.dto.NumeratorDto;

import java.time.LocalDate;
import java.util.List;

public class MigrationConfiguration {

    protected List<RegisterDto> getAccountingRegister() {
        return List.of(
            RegisterDto.create().code( "FS")
                .type( AccountingRegisterType.R.name()).name( "Faktury Sprzedaży"),
            RegisterDto.create().code( "BK")
                .type( AccountingRegisterType.R.name()).name( "Operacje Bankowe/Kasowe"),
            RegisterDto.create().code( "FK")
                .type( AccountingRegisterType.R.name()).name( "Faktury Kosztowe"),
            RegisterDto.create().code( "PK")
                .type( AccountingRegisterType.R.name()).name( "Polecenia Księgowania"),
            RegisterDto.create().code( "PL")
                .type( AccountingRegisterType.R.name()).name( "Płace")
        );
    }

}
