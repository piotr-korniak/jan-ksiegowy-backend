package pl.janksiegowy.backend.database;

import pl.janksiegowy.backend.shared.numerator.NumeratorType;
import pl.janksiegowy.backend.shared.numerator.dto.NumeratorDto;

public class MigrationConfiguration {
/*
    private final  account;

    protected getInitialAccount() {
        AccountDto[]=
    }*/

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
}
