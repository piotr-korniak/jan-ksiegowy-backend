package pl.janksiegowy.backend.report;

import pl.janksiegowy.backend.accounting.decree.DecreeLineQueryRepository;
import pl.janksiegowy.backend.shared.Util;
import pl.janksiegowy.backend.shared.interpreter.Interpreter;
import pl.janksiegowy.backend.tax.vat.ProfitAndLossItems;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDate;

public class BalanceSheet {

    private final DecreeLineQueryRepository decreeLines;
    private final ProfitAndLossItems profitAndLossItems;
    public static final DecimalFormat DWA_MIEJSCA= new DecimalFormat( "#,###,##0.00");

    public BalanceSheet(DecreeLineQueryRepository decreeLines, ProfitAndLossItems profitAndLossItems) {
        this.decreeLines= decreeLines;
        this.profitAndLossItems = profitAndLossItems;
    }

    public StringBuilder prepare( LocalDate startDate, LocalDate endDate) {
        var inter= profitAndLossItems.calculate( startDate, endDate);

        inter.setVariable( "Trwale", BigDecimal.ZERO);

        inter.setVariable( "SaldoWn100", decreeLines.parentBalanceDt( "100-[S]", startDate, endDate));
        inter.setVariable( "SaldoWn130", decreeLines.parentBalanceDt( "130-[B]", startDate, endDate));
        inter.setVariable( "SaldoMa130", decreeLines.developedBalanceCt("130-[B]", startDate, endDate));
        inter.setVariable( "SaldoWn201", decreeLines.developedBalanceDt( "201-[K]", startDate, endDate));
        inter.setVariable( "SaldoWn202", decreeLines.developedBalanceDt( "202-[K]", startDate, endDate));
        inter.setVariable( "SaldoWn221_1", decreeLines.balanceDtLike( "221-1", startDate, endDate));
        inter.setVariable( "SaldoWn221_3", decreeLines.balanceDtLike( "221-3", startDate, endDate));
        inter.setVariable( "SaldoWn221_2", decreeLines.balanceDtLike( "221-2", startDate, endDate));

        inter.sum( "Naleznosci", "SaldoWn201", "SaldoWn202", "SaldoWn221_2", "SaldoWn221_3");
        inter.sum( "Obrotowe", "Naleznosci", "SaldoWn100", "SaldoWn130");
        inter.setVariable( "SaldoWn241", decreeLines.parentBalanceDt( "241-[W]", startDate, endDate));

        System.err.println( "Saldo Wn 100: "+ inter.getVariable( "SaldoWn100"));
        System.err.println( "Saldo Wn 130: "+ inter.getVariable( "SaldoWn130"));
        System.err.println( "Saldo Ma 130: "+ inter.getVariable( "SaldoMa130"));
        System.err.println( "Saldo Wn 201: "+ inter.getVariable( "SaldoWn201"));
        System.err.println( "Saldo Wn 202: "+ inter.getVariable( "SaldoWn202"));
        System.err.println( "Saldo Wn 221_1: "+ inter.getVariable( "SaldoWn221_1"));
        System.err.println( "Saldo Wn 221_3: "+ inter.getVariable( "SaldoWn221_3"));
        System.err.println( "Saldo Wn 221_2: "+ inter.getVariable( "SaldoWn221_2"));

        System.err.println( "Saldo Wn 241: "+ inter.getVariable( "SaldoWn241"));

        inter.interpret( "Aktywa", "[Trwale]+[Obrotowe]+[SaldoWn241]");

        inter.setVariable( "SaldoMa801", decreeLines.balanceCtLike( "801", startDate, endDate));
        inter.sum( "Kapital", "SaldoMa801", "Wynik");

        System.err.println( "Wynik: "+ inter.getVariable( "Wynik"));

        inter.setVariable( "SaldoMa202", decreeLines.developedBalanceCt( "202-[K]", startDate, endDate));
        inter.setVariable( "SaldoMa201", decreeLines.developedBalanceCt( "201-[K]", startDate, endDate));
        inter.setVariable( "SaldoMa221_1", decreeLines.balanceCtLike( "221-1", startDate, endDate));
        inter.setVariable( "SaldoMa221_2", decreeLines.balanceCtLike( "221-2", startDate, endDate));
        inter.setVariable( "SaldoMa221_3", decreeLines.balanceCtLike( "221-3", startDate, endDate));
        inter.setVariable( "SaldoMa234", decreeLines.developedBalanceCt( "234-[P]", startDate, endDate));

        System.err.println( "Saldo Ma 202: "+ inter.getVariable( "SaldoMa202"));
        System.err.println( "Saldo Ma 201: "+ inter.getVariable( "SaldoMa201"));
        System.err.println( "Saldo Ma 221_1: "+ inter.getVariable( "SaldoMa221_1"));
        System.err.println( "Saldo Ma 221_2: "+ inter.getVariable( "SaldoMa221_2"));
        System.err.println( "Saldo Ma 221_3: "+ inter.getVariable( "SaldoMa221_3"));
        System.err.println( "Saldo Ma 234: "+ inter.getVariable( "SaldoMa234"));

    //   inter.setVariable( "SaldoMa248", decreeLines.developedBalanceCt( "248-[W]", startDate, endDate));

        inter.setVariable( "SaldoMa227", decreeLines.balanceCtLike( "227", startDate, endDate));

        System.err.println( "Saldo Jeszcze Raz!: "+ inter.getVariable( "SaldoMa221_1"));
        inter.sum( "Zobowiazania", "SaldoMa202", "SaldoMa201", "SaldoMa221_1", "SaldoMa221_3",
                "SaldoMa227", "SaldoMa234");
        inter.interpret( "Pasywa", "[Kapital]+[Zobowiazania]");

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
