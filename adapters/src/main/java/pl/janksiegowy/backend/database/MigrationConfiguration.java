package pl.janksiegowy.backend.database;

import pl.janksiegowy.backend.accounting.account.AccountSide;
import pl.janksiegowy.backend.accounting.account.dto.AccountDto;
import pl.janksiegowy.backend.accounting.account.AccountType;
import pl.janksiegowy.backend.accounting.template.*;
import pl.janksiegowy.backend.accounting.template.dto.TemplateDto;
import pl.janksiegowy.backend.accounting.template.dto.TemplateLineDto;
import pl.janksiegowy.backend.accounting.template.dto.TemplateMap;
import pl.janksiegowy.backend.entity.EntityType;
import pl.janksiegowy.backend.entity.dto.EntityDto;
import pl.janksiegowy.backend.finances.settlement.SettlementType;
import pl.janksiegowy.backend.register.accounting.AccountingRegisterType;
import pl.janksiegowy.backend.register.dto.RegisterDto;
import pl.janksiegowy.backend.register.payment.PaymentRegisterType;
import pl.janksiegowy.backend.salary.ContractType;
import pl.janksiegowy.backend.salary.dto.ContractDto;
import pl.janksiegowy.backend.shared.numerator.NumeratorCode;
import pl.janksiegowy.backend.shared.numerator.NumeratorType;
import pl.janksiegowy.backend.shared.numerator.dto.NumeratorDto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class MigrationConfiguration {

    protected List<ContractDto> getInitialContracts() {
        return List.of(
            ContractDto.create().type( ContractType.S)
                    .entity( EntityDto.create().taxNumber( "70103104660"))
                    .begin( LocalDate.of( 2017, 9, 1))
                    .end( LocalDate.of( 2017, 9, 30))
                    .number( "Umowa 1/2017")
                    .salary( new BigDecimal( "10000.00")),
            ContractDto.create().type( ContractType.S)
                    .entity( EntityDto.create().taxNumber( "70103104660"))
                    .begin( LocalDate.of( 2017, 10, 1))
                    .end( LocalDate.of( 2017, 10, 31))
                    .number( "Umowa 2/2017")
                    .salary( new BigDecimal( "10000.00")),
            ContractDto.create().type( ContractType.S)
                    .entity( EntityDto.create().taxNumber( "70103104660"))
                    .begin( LocalDate.of( 2017, 11, 1))
                    .end( LocalDate.of( 2017, 11, 30))
                    .number( "Umowa 3/2017")
                    .salary( new BigDecimal( "10000.00")),
            ContractDto.create().type( ContractType.S)
                    .entity( EntityDto.create().taxNumber( "70103104660"))
                    .begin( LocalDate.of( 2017, 12, 1))
                    .end( LocalDate.of( 2017, 12, 31))
                    .number( "Umowa 4/2017")
                    .salary( new BigDecimal( "10000.00")),
            ContractDto.create().type( ContractType.S)
                    .entity( EntityDto.create().taxNumber( "70103104660"))
                    .begin( LocalDate.of( 2018, 1, 1))
                    .end( LocalDate.of( 2018, 1, 31))
                    .number( "Umowa 1/2018")
                    .salary( new BigDecimal( "10000.00")),
            ContractDto.create().type( ContractType.E)
                    .entity( EntityDto.create().taxNumber( "70010402493"))
                    .begin( LocalDate.of( 2018, 2, 1))
                    .end( LocalDate.of( 2018, 12, 31))
                    .number( "Umowa 2/2018")
                    .salary( new BigDecimal( "525.00")),
            ContractDto.create().type( ContractType.S)
                    .entity( EntityDto.create().taxNumber( "70103104660"))
                    .begin( LocalDate.of( 2018, 3, 1))
                    .end( LocalDate.of( 2018, 3, 31))
                    .number( "Umowa 3/2018")
                    .salary( new BigDecimal( "10000.00")),
            ContractDto.create().type( ContractType.E)
                .entity( EntityDto.create().taxNumber( "70010402493"))
                .begin( LocalDate.of( 2020, 1, 1))
                .number( "Umowa 1/2023")
                .salary( new BigDecimal( "525.00"))
        );
    }

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

    protected List<AccountDto> getInitialAccount() {
        return List.of(
            AccountDto.create()
                .type( AccountType.B).number( "100-[D]").name( "Kasa"),
            AccountDto.create()
                .type( AccountType.B).number( "130-[A]").name( "Bank"),
            AccountDto.create()
                .type( AccountType.B).number( "201-[C]").name( "Rozrachunki z Dostawcami"),
            AccountDto.create()
                .type( AccountType.B).number( "202-[C]").name( "Rozrachunki z Odbiorcami"),
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
                .type( AccountType.P).number( "750-1").name( "Przychody finansowe PP"),
            AccountDto.create()
                .type( AccountType.P).number( "750-2").name( "Przychody finansowe NP"),
            AccountDto.create()
                .type( AccountType.P).number( "755-1").name( "Koszty finansowe KUP"),
            AccountDto.create()
                .type( AccountType.P).number( "755-2").name( "Koszty finansowe NUP"),
            AccountDto.create()
                .type( AccountType.P).number( "760").name( "Pozostałe przychody operacyjne"),
            AccountDto.create()
                .type( AccountType.P).number( "765").name( "Pozostałe koszty operacyjne"),
            AccountDto.create()
                .type( AccountType.P).number( "404").name( "Opłaty i podatki"),
            AccountDto.create()
                .type( AccountType.P).number( "405").name( "Wynagrodzenia"),
            AccountDto.create()
                .type( AccountType.P).number( "406").name( "Ubezpieczenia społeczne i świadczenia"),
            AccountDto.create()
                .type( AccountType.P).number( "409-1").name( "Pozostałe "),
            AccountDto.create()
                .type( AccountType.P).number( "409").name( "Pozostałe koszty"),
            AccountDto.create()
                .type( AccountType.P).number( "410").name( "Niestanowiące kosztów uzyskania przychodów"),
            AccountDto.create()
                .type( AccountType.B).number( "227").name( "Rozrachunki z tytułu PIT"),
            AccountDto.create()
                .type( AccountType.B).number( "225").name( "Rozrachunki z ZUS"),
            AccountDto.create()
                .type( AccountType.B).number( "234-[E]").name( "Rozrachunki z Pracownikami"),
            AccountDto.create()
                .type( AccountType.B).number( "248-[S]").name( "Pozostałe rozrachunki z Udziałowcami"),
            AccountDto.create()
                .type( AccountType.B).number( "801").name( "Kapitał zakładowy"),
            AccountDto.create()
                .type( AccountType.B).number( "241-[S]").name( "Należne wpłaty na poczet kapitału"),
            AccountDto.create()
                .type( AccountType.B).number( "860").name( "Wynik finansowy"),
            AccountDto.create()
                    .type( AccountType.B).number( "870").name( "Podatek dochodowy"),
            AccountDto.create()
                    .type( AccountType.B).number( "226").name( "Rozrachunki z tytułu CIT"),
            AccountDto.create()
                    .type( AccountType.B).number( "224-[R]").name( "Rozrachunki z Urzędem Skarbowym"),
            AccountDto.create()
                    .type( AccountType.P).number( "765-2").name( "Pozostałe Koszty operacyjne NKUP")
        );
    }

    protected List<TemplateMap> getInitialTemplates() {
        return List.of(
                new TemplateMap( TemplateDto.create().code( "BW/KW US")
                        .documentType( TemplateType.PE)
                        .entityType( EntityType.R)
                        .date( LocalDate.EPOCH)
                        .registerCode( "BK")
                        .name( "Bank/Kasa Wyda - Urząd Skarbowy"))
                        .add( TemplateLineDto.create()
                                .side( AccountSide.D)
                                .function( PaymentFunction.WartoscRozrachowania)
                                .account( AccountDto.create().number( "224-[R]"))
                                .settlementType( SettlementType.L)
                                .description( "zapłata kosztów"))
                        .add( TemplateLineDto.create()
                                .side( AccountSide.D)
                                .function( PaymentFunction.WartoscRozrachowania)
                                .account( AccountDto.create().number( "224-[R]"))
                                .settlementType( SettlementType.N)
                                .description( "zapłata noty"))
                        .add( TemplateLineDto.create()
                                .side( AccountSide.D)
                                .function( PaymentFunction.WartoscRozrachowania)
                                .account( AccountDto.create().number( "221-3"))
                                .settlementType( SettlementType.V)
                                .description( "zapłata VAT"))
                        .add( TemplateLineDto.create()
                                .side( AccountSide.C)
                                .function( PaymentFunction.WartoscRozrachowania)
                                .account( AccountDto.create().number( "100-[D]"))
                                .description( "wypłata z kasy"))
                        .add( TemplateLineDto.create()
                                .side( AccountSide.C)
                                .function( PaymentFunction.WartoscRozrachowania)
                                .account( AccountDto.create().number( "130-[A]"))
                                .description( "wypłata z banku")),

            new TemplateMap( TemplateDto.create().code( "BW/KW")
                    .documentType( TemplateType.PE )
                    .date( LocalDate.EPOCH)
                    .registerCode( "BK")
                    .name( "Bank/Kasa Wyda"))
                    .add( TemplateLineDto.create()
                            .side( AccountSide.D)
                            .function( PaymentFunction.SplataZobowiazania )
                            .account( AccountDto.create().number( "202-[C]"))
                            .description( "zapłata zobowiązania"))
                    .add( TemplateLineDto.create()
                            .side( AccountSide.D)
                            .function( PaymentFunction.SplataZobowiazania )
                            .account( AccountDto.create().number( "234-[E]"))
                            .description( "zapłata zobowiązania"))
                    .add( TemplateLineDto.create()
                            .side( AccountSide.D)
                            .function( PaymentFunction.SplataZobowiazania )
                            .account( AccountDto.create().number( "248-[S]"))
                            .description( "zapłata zobowiązania"))
                    .add( TemplateLineDto.create()
                            .side( AccountSide.C)
                            .function( PaymentFunction.SplataZobowiazania )
//                            .registerType( PaymentRegisterType.C)
                            .account( AccountDto.create().number( "100-[D]"))
                            .description( "wypłata z kasy"))
                    .add( TemplateLineDto.create()
                            .side( AccountSide.C)
                            .function( PaymentFunction.SplataZobowiazania )
//                            .registerType( PaymentRegisterType.B)
                            .account( AccountDto.create().number( "130-[A]"))
                            .description( "wypłata z banku"))
                    .add( TemplateLineDto.create()
                            .side( AccountSide.C)
                            .function( PaymentFunction.SplataNoty)
                            .account( AccountDto.create().number( "755-2"))
                            .description( "wyksięgowanie kosztów"))
                    .add( TemplateLineDto.create()
                            .side( AccountSide.D)
                            .function( PaymentFunction.SplataNoty)
                            .account( AccountDto.create().number( "755-1"))
                            .description( "zaksięgowanie kosztów"))
                    .add( TemplateLineDto.create()
                            .side( AccountSide.D)
                            .function( PaymentFunction.SplataVat)
                            .account( AccountDto.create().number( "221-3"))
                            .description( "spłata VAT"))
                    .add( TemplateLineDto.create()
                            .side( AccountSide.D)
                            .function( PaymentFunction.SplataNKUP)
                            .account( AccountDto.create().number( "755-2"))
                            .description( "zaksięgowanie kosztów NKUP")),
            new TemplateMap( TemplateDto.create().code( "BP/KP")
                    .documentType( TemplateType.PR)
                    .date( LocalDate.EPOCH)
                    .registerCode( "BK")
                    .name( "Bank/Kasa Przyjmie"))
                    .add( TemplateLineDto.create()
                            .side( AccountSide.C)
                            .settlementType( SettlementType.S)
                            .function( PaymentFunction.WplataNaleznosci)
                            .account( AccountDto.create().number( "201-[C]"))
                            .description( "wpłata należności za fakturę"))
                    .add( TemplateLineDto.create()
                            .side( AccountSide.C)
                            .settlementType( SettlementType.A)
                            .function( PaymentFunction.OplacenieUdzialow )
                            .account( AccountDto.create().number( "241-[S]"))
                            .description( "opłacenie udziałów"))
                    .add( TemplateLineDto.create()
                            .side( AccountSide.D)
                            .function( PaymentFunction.WplataNaleznosci)
                           // .registerType( PaymentRegisterType.D)
                            .account( AccountDto.create().number( "100-[D]"))
                            .description( "wpłata do kasy"))
                    .add( TemplateLineDto.create()
                            .side( AccountSide.D)
                            .function( PaymentFunction.WplataNaleznosci)
                           // .registerType( PaymentRegisterType.A)
                            .account( AccountDto.create().number( "130-[A]"))
                            .description( "wpłata do banku")),
            new TemplateMap( TemplateDto.create().code( "FS")
                    .documentType( TemplateType.IS)
                    .date( LocalDate.EPOCH)
                    .registerCode( "FS")
                    .name( "Faktura sprzedaży"))
                    .add( TemplateLineDto.create()
                            .side( AccountSide.D)
                            .function( InvoiceFunction.KwotaBurtto)
                            .account( AccountDto.create().number( "201-[C]"))
                            .description( "brutto faktury sprzedaży"))
                    .add( TemplateLineDto.create()
                            .side( AccountSide.C)
                            .function( InvoiceFunction.KwotaVAT)
                            .account( AccountDto.create().number( "221-1"))
                            .description( "podatek VAT należny"))
                    .add( TemplateLineDto.create()
                            .side( AccountSide.C)
                            .function( InvoiceFunction.KwotaUslugKUP )
                            .account( AccountDto.create().number( "703"))
                            .description( "sprzedaż usług")),
            new TemplateMap( TemplateDto.create().code( "FK")
                    .documentType( TemplateType.IP)
                    .date( LocalDate.EPOCH)
                    .registerCode( "FK")
                    .name( "Faktura kosztowa"))
                    .add( TemplateLineDto.create()
                            .side( AccountSide.C)
                            .function( InvoiceFunction.KwotaBurtto)
                            .account( AccountDto.create().number( "202-[C]"))
                            .description( "brutto faktury zakupu"))
                    .add( TemplateLineDto.create()
                            .side( AccountSide.D)
                            .function( InvoiceFunction.KwotaVAT)
                            .account( AccountDto.create().number( "221-2"))
                            .description( "podatek VAT naliczony"))
                    .add( TemplateLineDto.create()
                            .side( AccountSide.D)
                            .function( InvoiceFunction.KwotaMaterialowKUP )
                            .account( AccountDto.create().number( "402-1"))
                            .description( "koszty materiałów"))
                    .add( TemplateLineDto.create()
                            .side( AccountSide.D)
                            .function( InvoiceFunction.KwotaMaterialowNUP )
                            .account( AccountDto.create().number( "402-2"))
                            .description( "koszty materiałów bez odliczenia"))
                    .add( TemplateLineDto.create()
                            .side( AccountSide.D)
                            .function( InvoiceFunction.KwotaUslugKUP )
                            .account( AccountDto.create().number( "403-1"))
                            .description( "koszty usług obcych"))
                    .add( TemplateLineDto.create()
                            .side( AccountSide.D)
                            .function( InvoiceFunction.KwotaUslugNUP )
                            .account( AccountDto.create().number( "403-2"))
                            .description( "koszty usług bez odliczenia"))
                    .add( TemplateLineDto.create()
                            .side( AccountSide.D)
                            .function( InvoiceFunction.KwotaPozostaleKUP )
                            .account( AccountDto.create().number( "409-1"))
                            .description( "koszty pozostałe")),

        new TemplateMap( TemplateDto.create().code( "DC")
                        .documentType( TemplateType.SC)
                        .date( LocalDate.EPOCH)
                        .registerCode( "PK")
                        .name( "Deklaracja CIT-8"))
                .add( TemplateLineDto.create()
                        .side( AccountSide.D)
                        .function( StatementFunction.ZaliczkaCIT)
                        .account( AccountDto.create().number( "870"))
                        .description( "zaliczka na podatek CIT"))
                .add( TemplateLineDto.create()
                        .side( AccountSide.C)
                        .function( StatementFunction.ZaliczkaCIT)
                        .account( AccountDto.create().number( "226"))
                        .description( "zobowiązanie z tytułu CIT")),

        new TemplateMap( TemplateDto.create().code( "DV")
                        .documentType( TemplateType.SV)
                        .date( LocalDate.EPOCH)
                        .registerCode( "PK")
                        .name( "Deklaracja VAT"))
                    .add( TemplateLineDto.create()
                            .side( AccountSide.D)
                            .function( StatementFunction.PodatekNalezny)
                            .account( AccountDto.create().number( "221-1"))
                            .description( "podatek należny"))
                    .add( TemplateLineDto.create()
                            .side( AccountSide.C)
                            .function( StatementFunction.PodatekNaliczony)
                            .account( AccountDto.create().number( "221-2"))
                            .description( "podatek naliczony"))
                    .add( TemplateLineDto.create()
                            .side( AccountSide.C)
                            .function( StatementFunction.KorektaNaleznegoPlus)
                            .account( AccountDto.create().number( "760"))
                            .description( "zaokrąglenie należnego plus"))
                    .add( TemplateLineDto.create()
                            .side( AccountSide.D)
                            .function( StatementFunction.KorektaNaleznegoPlus)
                            .account( AccountDto.create().number( "221-1"))
                            .description( "zaokrąglenie należnego plus"))
                    .add( TemplateLineDto.create()
                            .side( AccountSide.D)
                            .function( StatementFunction.KorektaNaleznegoMinus)
                            .account( AccountDto.create().number( "765"))
                            .description( "zaokrąglenie należnego minus"))
                    .add( TemplateLineDto.create()
                            .side( AccountSide.C)
                            .function( StatementFunction.KorektaNaleznegoMinus)
                            .account( AccountDto.create().number( "221-1"))
                            .description( "zaokrąglenie należnego minus"))
                    .add( TemplateLineDto.create()
                            .side( AccountSide.D)
                            .function( StatementFunction.KorektaNaliczonegoPlus)
                            .account( AccountDto.create().number( "765"))
                            .description( "zaokrąglenie naliczonego plus"))
                    .add( TemplateLineDto.create()
                            .side( AccountSide.C)
                            .function( StatementFunction.KorektaNaliczonegoPlus)
                            .account( AccountDto.create().number( "221-2"))
                            .description( "zaokrąglenie naliczonego plus"))
                    .add( TemplateLineDto.create()
                            .side( AccountSide.C)
                            .function( StatementFunction.KorektaNaliczonegoMinus)
                            .account( AccountDto.create().number( "760"))
                            .description( "zaokrąglenie naliczonego minus"))
                    .add( TemplateLineDto.create()
                            .side( AccountSide.D)
                            .function( StatementFunction.KorektaNaliczonegoMinus)
                            .account( AccountDto.create().number( "221-2"))
                            .description( "zaokrąglenie naliczonego minus"))
                    .add( TemplateLineDto.create()
                            .side( AccountSide.C)
                            .function( StatementFunction.Zobowiazanie )
                            .account( AccountDto.create().number( "221-3"))
                            .description( "zobowiązanie z tytułu VAT")),
                new TemplateMap( TemplateDto.create().code( "EP")
                        .documentType( TemplateType.EP)
                        .date( LocalDate.EPOCH)
                        .registerCode( "PL")
                        .name( "Odcinek wypłaty"))
                    .add( TemplateLineDto.create()
                            .side( AccountSide.D)
                            .function( PayslipFunction.WynagrodzenieBrutto )
                            .account( AccountDto.create().number( "405"))
                            .description( "wynagrodzenie brutto"))
                    .add( TemplateLineDto.create()
                            .side( AccountSide.C)
                            .function( PayslipFunction.SkladkaPracownika)
                            .account( AccountDto.create().number( "225"))
                            .description( "składka Pracownika"))
                    .add( TemplateLineDto.create()
                            .side( AccountSide.C)
                            .function( PayslipFunction.UbezpieczenieZdrowotne)
                            .account( AccountDto.create().number( "225"))
                            .description( "ubezpieczenie zdrowotne"))
                    .add( TemplateLineDto.create()
                            .side( AccountSide.D)
                            .function( PayslipFunction.SkladkaPracodawcy)
                            .account( AccountDto.create().number( "406"))
                            .description( "składka Pracodawcy"))
                    .add( TemplateLineDto.create()
                            .side( AccountSide.C)
                            .function( PayslipFunction.SkladkaPracodawcy)
                            .account( AccountDto.create().number( "225"))
                            .description( "składka Pracodawcy"))
                    .add( TemplateLineDto.create()
                            .side( AccountSide.C)
                            .function( PayslipFunction.ZaliczkaPIT)
                            .account( AccountDto.create().number( "227"))
                            .description( "zaliczka PIT"))
                    .add( TemplateLineDto.create()
                            .side( AccountSide.C)
                            .function( PayslipFunction.DoWyplaty)
                            .account( AccountDto.create().number( "234-[E]"))
                            .description( "do wypłaty" )),
                new TemplateMap( TemplateDto.create().code( "NR")
                        .name( "Nota Odsetkowa Otrzymana")
                        .documentType( TemplateType.NR)
                        .date( LocalDate.EPOCH)
                        .registerCode( "PK"))
                    .add( TemplateLineDto.create()
                            .side( AccountSide.D)
                            .function( SettlementFunction.WartoscZobowiazania )
                            .account( AccountDto.create().number( "755-2"))
                            .description( "wartość noty otrzymanej"))
                    .add( TemplateLineDto.create()
                            .side( AccountSide.C)
                            .function( SettlementFunction.WartoscZobowiazania )
                            .account( AccountDto.create().number( "224-[R]"))
                            .description( "wartość noty otrzymanej"))
                        .add( TemplateLineDto.create()
                            .side( AccountSide.C)
                            .function( SettlementFunction.WartoscZobowiazania )
                            .account( AccountDto.create().number( "202-[C]"))
                            .description( "wartość noty otrzymanej")),
                new TemplateMap( TemplateDto.create().code( "CL")
                        .name( "Opłata, podatek")
                        .documentType( TemplateType.CL)
                        .date( LocalDate.EPOCH)
                        .registerCode( "PK"))
                    .add( TemplateLineDto.create()
                            .side( AccountSide.D)
                            .function( SettlementFunction.WartoscZobowiazania )
                            .account( AccountDto.create().number( "765-2"))
                            .description( "opłata, podatek"))
                    .add( TemplateLineDto.create()
                            .side( AccountSide.C)
                            .function( SettlementFunction.WartoscZobowiazania )
                            .account( AccountDto.create().number( "224-[R]"))
                            .description( "opłata, podatek")),
                new TemplateMap( TemplateDto.create().code( "CF")
                        .name( "Opłata")
                        .documentType( TemplateType.CF)
                        .date( LocalDate.EPOCH)
                        .registerCode( "PK"))
                    .add( TemplateLineDto.create()
                            .side( AccountSide.D)
                            .function( SettlementFunction.WartoscZobowiazania )
                            .account( AccountDto.create().number( "404"))
                            .description( "opłata, podatek"))
                    .add( TemplateLineDto.create()
                            .side( AccountSide.C)
                            .function( SettlementFunction.WartoscZobowiazania )
                            .account( AccountDto.create().number( "202-[C]"))
                            .description( "opłata, podatek"))
                    .add( TemplateLineDto.create()
                            .side( AccountSide.C)
                            .function( SettlementFunction.WartoscZobowiazania )
                            .account( AccountDto.create().number( "248-[S]"))
                            .description( "opłata, podatek")),
                new TemplateMap( TemplateDto.create().code( "HD")
                        .name( "Objęcie udziałów")
                        .documentType( TemplateType.HD)
                        .date( LocalDate.EPOCH)
                        .registerCode( "PK"))
                    .add( TemplateLineDto.create()
                            .side( AccountSide.D)
                            .function( SettlementFunction.WartoscNaleznosci)
                            .account( AccountDto.create().number( "241-[S]"))
                            .description( "objęcie udziałów"))
                    .add( TemplateLineDto.create()
                            .side( AccountSide.C)
                            .function( SettlementFunction.WartoscNaleznosci)
                            .account( AccountDto.create().number( "801"))
                            .description( "objęcie udziałów"))

        );
    }
}
