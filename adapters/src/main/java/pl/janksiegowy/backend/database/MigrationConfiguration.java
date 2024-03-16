package pl.janksiegowy.backend.database;

import pl.janksiegowy.backend.accounting.account.AccountPage;
import pl.janksiegowy.backend.accounting.account.dto.AccountDto;
import pl.janksiegowy.backend.accounting.account.AccountType;
import pl.janksiegowy.backend.accounting.template.DocumentType;
import pl.janksiegowy.backend.accounting.template.InvoiceFunction;
import pl.janksiegowy.backend.accounting.template.PaymentFunction;
import pl.janksiegowy.backend.accounting.template.StatementFunction;
import pl.janksiegowy.backend.accounting.template.dto.TemplateDto;
import pl.janksiegowy.backend.accounting.template.dto.TemplateLineDto;
import pl.janksiegowy.backend.accounting.template.dto.TemplateMap;
import pl.janksiegowy.backend.register.accounting.AccountingRegisterType;
import pl.janksiegowy.backend.register.dto.RegisterDto;
import pl.janksiegowy.backend.register.payment.PaymentRegisterType;
import pl.janksiegowy.backend.shared.numerator.NumeratorCode;
import pl.janksiegowy.backend.shared.numerator.NumeratorType;
import pl.janksiegowy.backend.shared.numerator.dto.NumeratorDto;

import java.time.LocalDate;
import java.util.List;

public class MigrationConfiguration {
    protected NumeratorDto[] getInitialNumerators() {
        return new NumeratorDto[] {
            NumeratorDto.create().code( NumeratorCode.BR)   // Bank Receipt
                .type( NumeratorType.Y).typed( true).mask( "BP %N/%T/%4Y").name( "Bank Przyjmie"),
            NumeratorDto.create().code( NumeratorCode.BS)   // Bank Spend
                .type( NumeratorType.Y).typed( true).mask( "BW %N/%T/%4Y").name( "Bank Wyda"),
            NumeratorDto.create().code( NumeratorCode.CR)   // Cash Receipt
                .type( NumeratorType.Y).typed( false).mask( "KP %N/%4Y").name( "Kasa Przyjmie"),
            NumeratorDto.create().code( NumeratorCode.CS)   // Cash Spend
                .type( NumeratorType.Y).typed( false).mask( "KW %N/%4Y").name( "Kasa Wyda"),
            NumeratorDto.create().code( NumeratorCode.SI)   // Sales Invoice
                .type( NumeratorType.Y).typed( false).mask( "FS %N/%4Y").name( "Faktura Sprzedaży"),
            NumeratorDto.create().code( NumeratorCode.EN)   // Entity
                .type( NumeratorType.E).typed( true).mask( "%5N").name( "Podmioty"),
            NumeratorDto.create().code( NumeratorCode.AC)   // Accounting Registers
                .type( NumeratorType.M).typed( true).mask( "%T %N/%M/%2Y").name( "Rejestry Księgowe"),
            NumeratorDto.create().code( NumeratorCode.RE)   // Accounting Registers
                .type( NumeratorType.E).typed( true).mask( "%2N").name( "Rejestry")
        };
    }

    protected List<RegisterDto> getAccountingRegister() {
        return List.of(
            RegisterDto.create().code( "FS")
                .type( AccountingRegisterType.A.name()).name( "Faktury Sprzedaży"),
            RegisterDto.create().code( "BK")
                .type( AccountingRegisterType.A.name()).name( "Operacje Bankowe/Kasowe"),
            RegisterDto.create().code( "FK")
                .type( AccountingRegisterType.A.name()).name( "Faktury Kosztowe"),
            RegisterDto.create().code( "PK")
                .type( AccountingRegisterType.A.name()).name( "Polecenia Księgowania")
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
                .type( AccountType.B).number( "202-[P]").name( "Rozrachunki z Odbiorcami"),
            AccountDto.create()
                .type( AccountType.P).number( "703").name( "Przychody ze sprzedaży usług"),
            AccountDto.create()
                .type( AccountType.B).number( "221-1").name( "Należny podatek VAT"),
            AccountDto.create()
                .type( AccountType.B).number( "221-2").name( "Naliczony podatek VAT"),
            AccountDto.create()
                .type( AccountType.B).number( "221-3").name( "Rozrachunki z tytułu VAT"),
            AccountDto.create()
                .type( AccountType.P).number( "402-1").name( "Zużycie materiałów KUP"),
            AccountDto.create()
                .type( AccountType.P).number( "402-2").name( "Zużycie materiałów NUP"),
            AccountDto.create()
                .type( AccountType.P).number( "403-1").name( "Usługi obce KUP"),
            AccountDto.create()
                .type( AccountType.P).number( "403-2").name( "Usługi obce NUP"),
            AccountDto.create()
                .type( AccountType.P).number( "760").name( "Pozostałe przychody operacyjne"),
            AccountDto.create()
                .type( AccountType.P).number( "765").name( "Pozostałe koszty operacyjne")
        );
    }

    protected List<TemplateMap> getInitialTemplates() {
        return List.of(
            new TemplateMap( TemplateDto.create().code( "BW/KW")
                    .documentType( DocumentType.PS)
                    .date( LocalDate.EPOCH)
                    .registerCode( "BK")
                    .name( "Bank/Kasa Wyda"))
                    .add( TemplateLineDto.create()
                            .page( AccountPage.C)
                            .function( PaymentFunction.SplataZobowiazania)
                            .account( AccountDto.create().number( "201-[P]")))
                    .add( TemplateLineDto.create()
                            .page( AccountPage.D)
                            .function( PaymentFunction.SplataZobowiazania)
                            .registerType( PaymentRegisterType.C)
                            .account( AccountDto.create().number( "100-[K]")))
                    .add( TemplateLineDto.create()
                            .page( AccountPage.D)
                            .function( PaymentFunction.SplataZobowiazania)
                            .registerType( PaymentRegisterType.B)
                            .account( AccountDto.create().number( "130-[B]"))),
            new TemplateMap( TemplateDto.create().code( "BP/KP")
                    .documentType( DocumentType.PR)
                    .date( LocalDate.EPOCH)
                    .registerCode( "BK")
                    .name( "Bank/Kasa Przyjmie"))
                    .add( TemplateLineDto.create()
                            .page( AccountPage.D)
                            .function( PaymentFunction.WplataNaleznosci)
                            .account( AccountDto.create().number( "201-[P]")))
                    .add( TemplateLineDto.create()
                            .page( AccountPage.C)
                            .function( PaymentFunction.WplataNaleznosci)
                            .registerType( PaymentRegisterType.C)
                            .account( AccountDto.create().number( "100-[K]")))
                    .add( TemplateLineDto.create()
                            .page( AccountPage.C)
                            .function( PaymentFunction.WplataNaleznosci)
                            .registerType( PaymentRegisterType.B)
                            .account( AccountDto.create().number( "130-[B]"))),
            new TemplateMap( TemplateDto.create().code( "FS")
                    .documentType( DocumentType.IS)
                    .date( LocalDate.EPOCH)
                    .registerCode( "FS")
                    .name( "Faktura sprzedaży"))
                    .add( TemplateLineDto.create()
                            .page( AccountPage.D)
                            .function( InvoiceFunction.KwotaBurtto)
                            .account( AccountDto.create().number( "202-[P]")))
                    .add( TemplateLineDto.create()
                            .page( AccountPage.C)
                            .function( InvoiceFunction.KwotaVAT)
                            .account( AccountDto.create().number( "221-2")))
                    .add( TemplateLineDto.create()
                            .page( AccountPage.C)
                            .function( InvoiceFunction.KwotaUslugKUP )
                            .account( AccountDto.create().number( "703"))),
            new TemplateMap( TemplateDto.create().code( "FK")
                    .documentType( DocumentType.IP)
                    .date( LocalDate.EPOCH)
                    .registerCode( "FK")
                    .name( "Faktura kosztowa"))
                    .add( TemplateLineDto.create()
                            .page( AccountPage.C)
                            .function( InvoiceFunction.KwotaBurtto)
                            .account( AccountDto.create().number( "201-[P]")))
                    .add( TemplateLineDto.create()
                            .page( AccountPage.D)
                            .function( InvoiceFunction.KwotaVAT)
                            .account( AccountDto.create().number( "221-1")))
                    .add( TemplateLineDto.create()
                            .page( AccountPage.D)
                            .function( InvoiceFunction.KwotaMaterialowKUP )
                            .account( AccountDto.create().number( "402-1")))
                    .add( TemplateLineDto.create()
                            .page( AccountPage.D)
                            .function( InvoiceFunction.KwotaMaterialowNUP )
                            .account( AccountDto.create().number( "402-2")))
                    .add( TemplateLineDto.create()
                            .page( AccountPage.D)
                            .function( InvoiceFunction.KwotaUslugKUP )
                            .account( AccountDto.create().number( "403-1")))
                    .add( TemplateLineDto.create()
                            .page( AccountPage.D)
                            .function( InvoiceFunction.KwotaUslugNUP )
                            .account( AccountDto.create().number( "403-2"))),
            new TemplateMap( TemplateDto.create().code( "DV")
                        .documentType( DocumentType.SV)
                        .date( LocalDate.EPOCH)
                        .registerCode( "PK")
                        .name( "Deklaracja VAT"))
                    .add( TemplateLineDto.create()
                        .page( AccountPage.D)
                        .function( StatementFunction.PodatekNalezny )
                        .account( AccountDto.create().number( "221-1")))
                    .add( TemplateLineDto.create()
                        .page( AccountPage.C)
                        .function( StatementFunction.PodatekNaliczony )
                        .account( AccountDto.create().number( "221-2")))
                    .add( TemplateLineDto.create()
                            .page( AccountPage.C)
                            .function( StatementFunction.KorektaNaleznegoPlus)
                            .account( AccountDto.create().number( "760")))
                    .add( TemplateLineDto.create()
                            .page( AccountPage.D)
                            .function( StatementFunction.KorektaNaleznegoPlus)
                            .account( AccountDto.create().number( "221-1")))
                    .add( TemplateLineDto.create()
                            .page( AccountPage.D)
                            .function( StatementFunction.KorektaNaleznegoMinus)
                            .account( AccountDto.create().number( "765")))
                    .add( TemplateLineDto.create()
                            .page( AccountPage.C)
                            .function( StatementFunction.KorektaNaleznegoMinus)
                            .account( AccountDto.create().number( "221-1")))
                    .add( TemplateLineDto.create()
                            .page( AccountPage.D)
                            .function( StatementFunction.KorektaNaliczonegoPlus)
                            .account( AccountDto.create().number( "765")))
                    .add( TemplateLineDto.create()
                            .page( AccountPage.C)
                            .function( StatementFunction.KorektaNaliczonegoPlus)
                            .account( AccountDto.create().number( "221-2")))
                    .add( TemplateLineDto.create()
                            .page( AccountPage.C)
                            .function( StatementFunction.KorektaNaliczonegoMinus)
                            .account( AccountDto.create().number( "760")))
                    .add( TemplateLineDto.create()
                            .page( AccountPage.D)
                            .function( StatementFunction.KorektaNaliczonegoMinus)
                            .account( AccountDto.create().number( "221-2")))
                    .add( TemplateLineDto.create()
                            .page( AccountPage.C)
                            .function( StatementFunction.Zobowiazanie )
                            .account( AccountDto.create().number( "221-3")))

        );
    }
}
