package pl.janksiegowy.backend.database;

import pl.janksiegowy.backend.accounting.account.AccountPage;
import pl.janksiegowy.backend.accounting.account.dto.AccountDto;
import pl.janksiegowy.backend.accounting.account.AccountType;
import pl.janksiegowy.backend.accounting.template.DocumentType;
import pl.janksiegowy.backend.accounting.template.TemplateType;
import pl.janksiegowy.backend.accounting.template.dto.TemplateDto;
import pl.janksiegowy.backend.accounting.template.dto.TemplateLineDto;
import pl.janksiegowy.backend.accounting.template.dto.TemplateMap;
import pl.janksiegowy.backend.register.accounting.AccountingRegisterType;
import pl.janksiegowy.backend.register.dto.RegisterDto;
import pl.janksiegowy.backend.shared.numerator.NumeratorType;
import pl.janksiegowy.backend.shared.numerator.dto.NumeratorDto;

import java.time.LocalDate;
import java.util.List;

public class MigrationConfiguration {
    protected NumeratorDto[] getInitialNumerators() {
        return new NumeratorDto[] {
            NumeratorDto.create().code( "BR")   // Bank Receipt0
                .type( NumeratorType.Y).typed( true).mask( "BP %N/%T/%4Y").name( "Bank Przyjmie"),
            NumeratorDto.create().code( "BS")   // Bank Spend
                .type( NumeratorType.Y).typed( true).mask( "BW %N/%T/%4Y").name( "Bank Wyda"),
            NumeratorDto.create().code( "CR")   // Cash Receipt
                .type( NumeratorType.Y).typed( false).mask( "KP %N/%4Y").name( "Kasa Przyjmie"),
            NumeratorDto.create().code( "CS")   // Cash Spend
                .type( NumeratorType.Y).typed( false).mask( "KW %N/%4Y").name( "Kasa Wyda"),
            NumeratorDto.create().code( "SI")   // Sales Invoice
                .type( NumeratorType.Y).typed( false).mask( "FS %N/%4Y").name( "Faktura Sprzedaży"),
            NumeratorDto.create().code( "EN")
                .type( NumeratorType.E).typed( true).mask( "%5N").name( "Podmioty")
        };
    }

    protected List<RegisterDto> getAccountingRegister() {
        return List.of(
            RegisterDto.create().code( "FS")
                .type( AccountingRegisterType.I.name()).name( "Faktury Sprzedaży"),
            RegisterDto.create().code( "KA")
                .type( AccountingRegisterType.Y.name()).name( "Operacje Kasowe"),
            RegisterDto.create().code( "BA")
                .type( AccountingRegisterType.Y.name()).name( "Operacje Bankowe")
        );
    }

    protected List<AccountDto> getInitialAccount() {
        return List.of(
            AccountDto.create()
                .type( AccountType.B).number( "100-[K]").name( "Kasa"),
            AccountDto.create()
                .type( AccountType.B).number( "130-[B]").name( "Bank"),
            AccountDto.create()
                .type( AccountType.B).number( "201-[P]").name( "Rozrachunki z Dostawcami"),
            AccountDto.create()
                .type( AccountType.B).number( "130-[P]").name( "Rozrachunki z Odbiorcami")
        );
    }

    protected List<TemplateMap> getInitialTemplates() {
        return List.of(

                new TemplateMap( TemplateDto.create().code( "KW")
                                .type( DocumentType.CS)
                                .date( LocalDate.EPOCH)
                                .register( RegisterDto.create().code( "KA"))
                                .name( "Kasa Wyda"))
                        .add( TemplateLineDto.create().type( TemplateType.C)
                                .page( AccountPage.C)
                                .function( "SplataZobowiazania")
                                .account( AccountDto.create().number( "201-[P]")))
                        .add( TemplateLineDto.create().type( TemplateType.C)
                                .page( AccountPage.D)
                                .function( "SplataZobowiazania")
                                .account( AccountDto.create().number( "100-[K]")))
        );
    }
}
