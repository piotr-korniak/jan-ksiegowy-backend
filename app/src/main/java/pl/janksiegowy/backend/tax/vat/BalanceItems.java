package pl.janksiegowy.backend.tax.vat;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.janksiegowy.backend.accounting.decree.DecreeLineQueryRepository;
import pl.janksiegowy.backend.shared.interpreter.Interpreter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
@AllArgsConstructor
public class BalanceItems {
    private final DecreeLineQueryRepository lines;
    private final ProfitAndLossItems profitAndLossItems;

    public Interpreter calculate( LocalDate beginPeriod, LocalDate endPeriod) {
        // System.err.println( "Kasa: "+  lines.parentBalanceDt( "100-[B]", beginPeriod, endPeriod));
        // System.err.println( "Bank: "+  lines.parentBalanceDt( "130-[B]", beginPeriod, endPeriod));
        // System.err.println( "US: "+ lines.developedBalanceCt( "224-[U]", beginPeriod, endPeriod));

        return profitAndLossItems.calculate( beginPeriod, endPeriod)
                .setVariable( "Trwale", BigDecimal.ZERO)
                .setVariable( "SaldoWn100", lines.parentBalanceDt( "100-[D]", beginPeriod, endPeriod))
                .setVariable( "SaldoWn130", lines.parentBalanceDt( "130-[A]", beginPeriod, endPeriod))
                .setVariable( "SaldoMa130", lines.developedBalanceCt("130-[A]", beginPeriod, endPeriod))
                .setVariable( "SaldoWn201", lines.developedBalanceDt( "201-[C]", beginPeriod, endPeriod))
                .setVariable( "SaldoWn202", lines.developedBalanceDt( "202-[C]", beginPeriod, endPeriod))
                .setVariable( "SaldoWn221_1", lines.balanceDtLike( "221-1", beginPeriod, endPeriod))
                .setVariable( "SaldoWn221_3", lines.balanceDtLike( "221-3", beginPeriod, endPeriod))
                .setVariable( "SaldoWn221_2", lines.balanceDtLike( "221-2", beginPeriod, endPeriod))
                .sum( "Naleznosci", "SaldoWn201", "SaldoWn202", "SaldoWn221_2", "SaldoWn221_3")
                .sum( "Obrotowe", "Naleznosci", "SaldoWn100", "SaldoWn130")

                .setVariable( "SaldoWn241", lines.parentBalanceDt( "241-[H]", beginPeriod, endPeriod))
                .interpret( "Aktywa", "[Trwale]+[Obrotowe]+[SaldoWn241]")
                .setVariable( "SaldoMa801", lines.balanceCtLike( "801", beginPeriod, endPeriod))
                .setVariable( "SaldoMa860", lines.balanceCtLike( "860", beginPeriod, endPeriod))
                .setVariable( "SaldoWn860", lines.balanceDtLike( "860", beginPeriod, endPeriod).negate())
                .sum( "Kapital", "SaldoMa801", "SaldoMa860", "SaldoWn860", "ZyskNetto")

                .setVariable( "SaldoMa202", lines.developedBalanceCt( "202-[C]", beginPeriod, endPeriod))
                .setVariable( "SaldoMa201", lines.developedBalanceCt( "201-[C]", beginPeriod, endPeriod))
                .setVariable( "SaldoMa221_1", lines.balanceCtLike( "221-1", beginPeriod, endPeriod))
                .setVariable( "SaldoMa221_2", lines.balanceCtLike( "221-2", beginPeriod, endPeriod))
                .setVariable( "SaldoMa221_3", lines.balanceCtLike( "221-3", beginPeriod, endPeriod))
                .setVariable( "SaldoMa224", lines.developedBalanceCt( "224-[O]", beginPeriod, endPeriod))
                .setVariable( "SaldoMa225", lines.balanceCtLike( "225", beginPeriod, endPeriod))
                .setVariable( "SaldoMa226", lines.balanceCtLike( "226", beginPeriod, endPeriod))
                .setVariable( "SaldoMa234", lines.developedBalanceCt( "234-[E]", beginPeriod, endPeriod))
        //   inter.setVariable( "SaldoMa248", decreeLines.developedBalanceCt( "248-[W]", startDate, endDate));
                .setVariable( "SaldoMa227", lines.balanceCtLike( "227", beginPeriod, endPeriod))
                .sum( "Zobowiazania", "SaldoMa202", "SaldoMa201", "SaldoMa221_1",
                        "SaldoMa221_3", "SaldoMa224", "SaldoMa225", "SaldoMa226", "SaldoMa227", "SaldoMa234")
                .interpret( "Pasywa", "[Kapital]+ [Zobowiazania]");
    }
}
