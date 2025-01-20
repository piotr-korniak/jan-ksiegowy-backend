package pl.janksiegowy.backend.tax.vat;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.janksiegowy.backend.accounting.decree.DecreeLineQueryRepository;
import pl.janksiegowy.backend.period.Period;
import pl.janksiegowy.backend.shared.Util;
import pl.janksiegowy.backend.shared.interpreter.Interpreter;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDate;
@Component
@AllArgsConstructor
public class ProfitAndLossItems {

    private final DecreeLineQueryRepository lines;

    public Interpreter calculate( LocalDate beginPeriod, LocalDate endPeriod) {
        
        return new Interpreter()
                .setVariable( "703", lines.turnoverCt( "703", beginPeriod, endPeriod))
                .interpret( "Przychody", "[703]")
                .setVariable( "402", lines.turnoverDt( "402%", beginPeriod, endPeriod))
                .setVariable( "403", lines.turnoverDt( "403%", beginPeriod, endPeriod))
                .setVariable( "404", lines.turnoverDt( "404%", beginPeriod, endPeriod))
                .setVariable( "405", lines.turnoverDt( "405", beginPeriod, endPeriod))
                .setVariable( "406", lines.turnoverDt( "406", beginPeriod, endPeriod))
                .setVariable( "409", lines.turnoverDt( "409%", beginPeriod, endPeriod))
                .interpret( "KosztyOperacyjne", "[402]+ [403]+ [404]+ [405]+ [406]+ [409]")
                .interpret( "ZyskZeSprzedazy", "[Przychody]- [KosztyOperacyjne]")
                .setVariable( "760", lines.turnoverCt( "760%", beginPeriod, endPeriod))
                .setVariable( "765", lines.turnoverDt( "765%", beginPeriod, endPeriod))
                .interpret( "ZyskOperacyjny", "[ZyskZeSprzedazy]+ [760]- [765]")
                .setVariable( "750", lines.turnoverDt( "750%", beginPeriod, endPeriod))
                .setVariable( "755", lines.turnoverDt( "755%", beginPeriod, endPeriod)
                        .subtract( lines.turnoverCt( "755-2", beginPeriod, endPeriod)))
                .setVariable( "402_2", lines.turnoverDt( "402-2", beginPeriod, endPeriod))
                .setVariable( "403_2", lines.turnoverDt( "403-2", beginPeriod, endPeriod))
                .setVariable( "750_1", lines.turnoverDt( "750-1", beginPeriod, endPeriod))
                .setVariable( "750_2", lines.turnoverDt( "750-2", beginPeriod, endPeriod)
                        .subtract( lines.turnoverCt( "750-2", beginPeriod, endPeriod) ))
                .setVariable( "755_1", lines.turnoverDt( "755-1", beginPeriod, endPeriod))
                .setVariable( "755_2", lines.turnoverDt( "755-2", beginPeriod, endPeriod)
                        .subtract( lines.turnoverCt( "755-2", beginPeriod, endPeriod)))
                .setVariable( "760_1", lines.turnoverDt( "760-1", beginPeriod, endPeriod))
                .setVariable( "760_2", lines.balanceDtLike( "760-2", beginPeriod, endPeriod))
                .setVariable( "765_1", lines.turnoverDt( "765-1", beginPeriod, endPeriod))
                .setVariable( "765_2", lines.balanceDtLike( "765-2", beginPeriod, endPeriod))
                .interpret( "NKUP", "[402_2]+ [403_2]+ [755_2]+ [765_2]")
                .interpret( "Podstawa", "[ZyskOperacyjny]+ [NKUP]+ [750_1]- [755_1]- [765_1]")
                .interpret( "ZyskBrutto", "[ZyskOperacyjny]+ [750]- [755]")
                .setVariable( "Zaliczki",
                        lines.turnoverDt( "870", beginPeriod, endPeriod.minusMonths(1)))
                .setVariable( "Podatek", lines.turnoverDt( "870", beginPeriod, endPeriod))
                .interpret( "ZyskNetto", "[ZyskBrutto]- [Podatek]");

    }


}
