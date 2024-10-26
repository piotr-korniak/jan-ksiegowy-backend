package pl.janksiegowy.backend.report;

import pl.janksiegowy.backend.accounting.decree.DecreeLineQueryRepository;
import pl.janksiegowy.backend.shared.Util;
import pl.janksiegowy.backend.shared.interpreter.Interpreter;
import pl.janksiegowy.backend.tax.vat.BalanceItems;
import pl.janksiegowy.backend.tax.vat.ProfitAndLossItems;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDate;

public class BalanceSheet {

    private final BalanceItems balanceItems;
    public static final DecimalFormat DWA_MIEJSCA= new DecimalFormat( "#,###,##0.00");

    public BalanceSheet( final BalanceItems balanceItems) {
        this.balanceItems= balanceItems;
    }

    public StringBuilder prepare( LocalDate startDate, LocalDate endDate) {
        var inter= balanceItems.calculate( startDate, endDate);
        var kontrola= inter.interpret( "[Aktywa]-[Pasywa]");

        return new StringBuilder()
                .append( String.format( "%-42s%10s - %10s\n", "Bilans",
                        Util.toString( startDate), Util.toString( endDate)))
                .append( toPrint( "Aktywa", inter.getVariable( "Aktywa")))
                .append( toPrint( "B. Aktywa obrotowe, w tym:", inter.getVariable( "Obrotowe")))
                .append( toPrint( "   - należność krótkoterminowe", inter.getVariable( "Naleznosci")))
                .append( toPrint( "C. Należne wpłaty na kapitał zakładowy", inter.getVariable( "SaldoWn241")))
                .append( toPrint( "Pasywa", inter.getVariable( "Pasywa")))
                .append( toPrint( "A. Kapitał (fundusz) własny, w tym:", inter.getVariable( "Kapital")))
                .append( toPrint( "   - kapitał (fundusz) podstawowy", inter.getVariable( "SaldoMa801")))
                .append( toPrint( "B. Zobowiązania i rezerwy na zobowiązania, w tym:",
                        inter.getVariable( "Zobowiazania")))
                .append( kontrola.signum()!=0?
                        "\nUwaga! \nBilans niezgodny na kwotę: "+ DWA_MIEJSCA.format( kontrola): "");
    }

    private String toPrint( String label, BigDecimal value){
        return String.format( "%-50s%15s\n", label, DWA_MIEJSCA.format( value ));
    }
}
