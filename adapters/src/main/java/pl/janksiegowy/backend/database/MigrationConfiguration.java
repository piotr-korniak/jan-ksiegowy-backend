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

    protected NumeratorDto[] getInitialNumerators() {
        return new NumeratorDto[] {
            NumeratorDto.create().code( NumeratorCode.BR)   // Bank Receipt
                .type( NumeratorType.M).typed( true).mask( "BP %T/%2Y/%M/%3N").name( "Bank Przyjmie"),
            NumeratorDto.create().code( NumeratorCode.BS)   // Bank Spend
                .type( NumeratorType.M).typed( true).mask( "BW %T/%2Y/%M/%3N").name( "Bank Wyda"),
            NumeratorDto.create().code( NumeratorCode.CR)   // Cash Receipt
                .type( NumeratorType.M).typed( false).mask( "KP %2Y/%M/%3N").name( "Kasa Przyjmie"),
            NumeratorDto.create().code( NumeratorCode.CS)   // Cash Spend
                .type( NumeratorType.M).typed( false).mask( "KW %2Y/%M/%3N").name( "Kasa Wyda"),
            NumeratorDto.create().code( NumeratorCode.SI)   // Sales Invoice
                .type( NumeratorType.Y).typed( false).mask( "FS %N/%4Y").name( "Faktura Sprzedaży"),
            NumeratorDto.create().code( NumeratorCode.EN)   // Entity
                .type( NumeratorType.E).typed( true).mask( "%5N").name( "Podmioty"),
            NumeratorDto.create().code( NumeratorCode.AC)   // Accounting Registers
                .type( NumeratorType.M).typed( true).mask( "%T %2Y/%M/%3N").name( "Rejestry Księgowe"),
            NumeratorDto.create().code( NumeratorCode.RE)   // Accounting Registers
                .type( NumeratorType.E).typed( true).mask( "%2N").name( "Rejestry"),
            NumeratorDto.create().code( NumeratorCode.ST)   // Statement
                .type( NumeratorType.M).typed( true).mask( "%3N").name( "Deklaracje")
        };
    }

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
