package pl.janksiegowy.backend.report;

import pl.janksiegowy.backend.accounting.decree.DecreeLineQueryRepository;
import pl.janksiegowy.backend.shared.Util;
import pl.janksiegowy.backend.shared.interpreter.Interpreter;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDate;

public class BalanceSheet {

    private final DecreeLineQueryRepository decreeLines;
    public static final DecimalFormat DWA_MIEJSCA= new DecimalFormat( "#,###,##0.00");

    public BalanceSheet( DecreeLineQueryRepository decreeLines) {
        this.decreeLines= decreeLines;
    }

    public StringBuilder prepare( LocalDate startDate, LocalDate endDate) {
        var inter= new Interpreter();

        inter.setVariable( "Trwale", BigDecimal.ZERO);

        inter.setVariable( "SaldoWn100", decreeLines.parentBalanceDt( "100-[K]", startDate, endDate));
        inter.setVariable( "SaldoWn130", decreeLines.parentBalanceDt( "130-[B]", startDate, endDate));
        inter.setVariable( "SaldoWn201", decreeLines.developedBalanceDt( "201-[P]", startDate, endDate));
        inter.setVariable( "SaldoWn221_1", decreeLines.balanceDtLike( "221-1", startDate, endDate));
        inter.setVariable( "SaldoWn221_3", decreeLines.balanceDtLike( "221-3", startDate, endDate));

        System.err.println( "Saldo kasy: "+ inter.getVariable( "SaldoWn100"));
        System.err.println( "Saldo bank: "+ inter.getVariable( "SaldoWn130"));
        System.err.println( "Obroty Wn 221-1: "+ decreeLines.turnoverDt("221-1", startDate, endDate));
        System.err.println( "Saldo Wn 221-1: "+ inter.getVariable( "SaldoWn221_1"));
        System.err.println( "Saldo Wn 221-3: "+ inter.getVariable( "SaldoWn221_3"));

        inter.interpret( "Naleznosci", "[SaldoWn100]+ [SaldoWn201]+ [SaldoWn221_1]+ [SaldoWn221_3]");
        inter.interpret( "Obrotowe", "[Naleznosci]");
        inter.setVariable( "SaldoWn241", decreeLines.parentBalanceDt( "241-[W]", startDate, endDate));

        inter.interpret( "Aktywa", "[Trwale]+[Obrotowe]+[SaldoWn241]");

        inter.setVariable( "SaldoMa801", decreeLines.balanceCtLike( "801", startDate, endDate));
        inter.setVariable( "Koszty", decreeLines.balanceDtLike( "40%", startDate, endDate));
        inter.setVariable( "SaldoMa703", decreeLines.balanceCtLike( "703", startDate, endDate));
        inter.setVariable( "SaldoMa760", decreeLines.balanceCtLike( "760", startDate, endDate));
        inter.interpret( "Przychody", "[SaldoMa703]+ [SaldoMa760]");
        inter.interpret( "Wynik", "[Przychody]-[Koszty]");
        inter.interpret( "Kapital", "[SaldoMa801]+[Wynik]");

        System.err.println( "Wynik: "+ inter.getVariable( "Wynik"));

        inter.setVariable( "SaldoMa202", decreeLines.developedBalanceCt( "202-[P]", startDate, endDate));
        inter.setVariable( "SaldoMa221_2", decreeLines.balanceCtLike( "221-2", startDate, endDate));
        inter.setVariable( "SaldoMa234", decreeLines.developedBalanceCt( "234-[P]", startDate, endDate));

        System.err.println( "234: "+ decreeLines.developedBalanceCt( "234-[P]", startDate, endDate));

    //   inter.setVariable( "SaldoMa248", decreeLines.developedBalanceCt( "248-[W]", startDate, endDate));

        inter.setVariable( "SaldoMa227", decreeLines.balanceCtLike( "227", startDate, endDate));

        System.err.println( "Saldo Ma 221-2: "+ inter.getVariable( "SaldoMa221_2"));

        //inter.interpret( "Zobowiazania", "[SaldoMa202]+ [SaldoMa221_2]+ [SaldoMa227]+ [SaldoMa234]");
        inter.sum( "Zobowiazania", "SaldoMa202", "SaldoMa221_2", "SaldoMa227", "SaldoMa234");
        inter.interpret( "Pasywa", "[Kapital]+[Zobowiazania]");

        var kontrola= inter.interpret( "[Aktywa]-[Pasywa]");

        return new StringBuilder()
                .append( String.format( "%-42s%10s - %10s\n", "Bilans",
                        Util.toString( startDate), Util.toString( endDate)))
                .append( toPrint( "Aktywa", inter.getVariable( "Aktywa")))
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
