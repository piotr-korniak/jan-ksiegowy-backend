package pl.janksiegowy.backend.report;

import pl.janksiegowy.backend.shared.Util;
import pl.janksiegowy.backend.tax.vat.ProfitAndLossItems;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDate;

public class ProfitAndLostSheet {

    public static final DecimalFormat DWA_MIEJSCA= new DecimalFormat( "#,###,##0.00");

    private final ProfitAndLossItems profitAndLossItems;

    public ProfitAndLostSheet( ProfitAndLossItems profitAndLossItems) {
        this.profitAndLossItems = profitAndLossItems;
    }

    public StringBuilder prepare(LocalDate startDate, LocalDate endDate) {
        var inter= profitAndLossItems.calculate( startDate, endDate );
        return new StringBuilder()
                .append( String.format( "\n%-47s%10s - %10s\n\n", "Rachunek Zysków i Strat (kalkulacyjny)",
                        Util.toString( startDate), Util.toString( endDate)))
                .append( toPrint( "A. Przychody netto ze sprzedaży i zrównane z nimi:",
                        inter.getVariable( "Przychody")))
                .append( toPrint( "      I. Przychody netto ze sprzedaży produktów",
                        inter.getVariable( "703")))
                .append( toPrint( "B. Koszty działalności operacyjnej",
                        inter.getVariable("KosztyOperacyjne")))
                .append( toPrint( "     II. Zużycie materiałów i energii", inter.getVariable("402")))
                .append( toPrint( "    III. Usługi obce", inter.getVariable("403")))
                .append( toPrint( "     IV. Podatki i opłaty", inter.getVariable("404")))
                .append( toPrint( "      V. Wynagrodzenia", inter.getVariable("405")))
                .append( toPrint( "     VI. Ubezpieczenia społeczne i inne świadczenia",
                        inter.getVariable("406")))
                .append( toPrint( "C. Zysk (strata) ze sprzedaży (A–B)",
                        inter.getVariable("ZyskZeSprzedazy")))
                .append( toPrint( "D. Pozostałe przychody operacyjne", inter.getVariable( "760")))
                .append( toPrint( "E. Pozostałe koszty operacyjne", inter.getVariable( "765")))
                .append( toPrint( "F. Zysk (strata) z działalności operacyjnej (C+D–E)",
                        inter.getVariable("ZyskOperacyjny")))
                .append( toPrint( "G. Przychody finansowe",  inter.getVariable( "750")))
                .append( toPrint( "    II. Odsetki", inter.getVariable( "750")))
                .append( toPrint( "H. Koszty finansowe", inter.getVariable( "755")))
                .append( toPrint( "     I. Odsetki", inter.getVariable( "755")))
                .append( toPrint( "I. Zysk (strata) brutto (F+G–H)", inter.getVariable( "ZyskBrutto")))
                .append( toPrint( "J. Podatek dochodowy", inter.getVariable( "Podatek", BigDecimal.ZERO)))
                .append( toPrint( "K. Pozostałe obowiązkowe zmniejszenia zysku (straty)", BigDecimal.ZERO))
                .append( toPrint( "L. Zysk (strata) netto (I–J–K)", inter.getVariable("ZyskNetto")));

    }

    private String toPrint( String label, BigDecimal value){
        return String.format( "%-58s%12s\n", label, DWA_MIEJSCA.format( value ));
    }
}
