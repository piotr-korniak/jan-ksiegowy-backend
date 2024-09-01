package pl.janksiegowy.backend.tax.vat;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.janksiegowy.backend.accounting.decree.DecreeLineQueryRepository;
import pl.janksiegowy.backend.shared.interpreter.Interpreter;

import java.time.LocalDate;
@Component
@AllArgsConstructor
public class ProfitAndLossItems {

    private final DecreeLineQueryRepository lines;

    public Interpreter calculate( LocalDate beginPeriod, LocalDate endPeriod) {
        return new Interpreter()
                .setVariable( "703", lines.turnoverCt( "703", beginPeriod, endPeriod))
                .setVariable( "760", lines.turnoverCt( "760", beginPeriod, endPeriod))
                .setVariable( "750_1", lines.turnoverDt( "750-1", beginPeriod, endPeriod))
                .interpret( "Przychody", "[703]+ [760]+ [750_1]")
                .setVariable( "402_1", lines.turnoverDt( "402-1", beginPeriod, endPeriod))
                .setVariable( "403_1", lines.turnoverDt( "403-1", beginPeriod, endPeriod))
                .setVariable( "404", lines.turnoverDt( "404", beginPeriod, endPeriod))
                .setVariable( "405", lines.turnoverDt( "405", beginPeriod, endPeriod))
                .setVariable( "409_1", lines.turnoverDt( "409-1", beginPeriod, endPeriod))
                .setVariable( "755_1", lines.turnoverDt( "755-1", beginPeriod, endPeriod))
                .setVariable( "765", lines.turnoverDt( "765", beginPeriod, endPeriod))
                .interpret( "Koszty_KUP", "[402_1]+ [403_1]+ [404]+ [405]+ [409_1]+ [755_1]+ [765]")
                .interpret( "Wynik", "[Przychody]- [Koszty_KUP]")
                .setVariable( "402_2", lines.turnoverDt( "402-2", beginPeriod, endPeriod))
                .setVariable( "403_2", lines.turnoverDt( "403-2", beginPeriod, endPeriod))
                .setVariable( "750_2", lines.turnoverDt( "750-2", beginPeriod, endPeriod))
                .setVariable( "755_2", lines.turnoverDt( "755-2", beginPeriod, endPeriod))
                .interpret( "Koszty_NUP", "[402_2]+ [403_2]+ [750_2]+ [755_2]");
    }
}
