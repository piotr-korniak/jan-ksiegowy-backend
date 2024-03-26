package pl.janksiegowy.backend.statement;

import lombok.AllArgsConstructor;
import pl.janksiegowy.backend.accounting.decree.DecreeLineQueryRepository;
import pl.janksiegowy.backend.period.PeriodRepository;
import pl.janksiegowy.backend.shared.interpreter.DecreeLineFunction;
import pl.janksiegowy.backend.shared.interpreter.Interpreter;

import java.math.BigDecimal;
import java.text.DecimalFormat;

@AllArgsConstructor
public class CitApproval {

    private final DecreeLineQueryRepository lines;
    private final PeriodRepository periods;

    public static final DecimalFormat DWA_MIEJSCA= new DecimalFormat( "#,###,##0.00");

    public StringBuilder approval( String periodId) {

        return periods.findById( periodId).map( period-> {

            var inter =new Interpreter()
                    .setVariable( "podatek", new BigDecimal( "0.09"))
                    .setVariable( "703", lines.turnoverCt( period, "703"))
                    .setVariable( "760", lines.turnoverCt( period, "760"))
                    .setVariable( "750_1", lines.turnoverDt( period, "750-1"))
                    .interpret( "przychody", "[703]+ [760]+ [750_1]")
                    .setVariable( "402_1", lines.turnoverDt( period,"402-1"))
                    .setVariable( "403_1", lines.turnoverDt( period,"403-1"))
                    .setVariable( "404", lines.turnoverDt( period, "404"))
                    .setVariable( "405", lines.turnoverDt( period, "405"))
                    .setVariable( "755_1", lines.turnoverDt( period, "755-1"))
                    .setVariable( "765", lines.turnoverCt( period, "765"))
                    .interpret( "koszty", "[402_1]+ [403_1]+ [404]+ [405]+ [755_1]+ [765]")
                    .setVariable( "JEDEN", BigDecimal.ONE)
                    .interpret( "wynik", "[przychody]- [koszty]@ [JEDEN]")
                    .interpret( "podatek", "[wynik]@ [podatek]")
                    .setVariable( "750_2", lines.turnoverDt( period, "750-2"))
                    .setVariable( "755_2", lines.turnoverDt( period, "755-2"))
                    .setVariable( "402_2", lines.turnoverDt( period, "402-2"))
                    .setVariable( "403_2", lines.turnoverDt( period, "403-2"))
                    .interpret( "koszty_NUP", "[755_2]+ [402_2]+ [403_2]")
                    .interpret( "zysk_strata", "[wynik]-[podatek]+ [750_2]- [koszty_NUP]" );


            return new StringBuilder()
                    .append( toPrint( "A. Przychody", inter.getVariable( "przychody")))
                    .append( toPrint( "   - operacyjne", inter.getVariable( "703")))
                    .append( toPrint( "   - pozostałe", inter.getVariable( "760")))
                    .append( toPrint( "   - finansowe", inter.getVariable( "750_1")))
                    .append( toPrint( "B. Koszty", inter.getVariable( "koszty" )))
                    .append( toPrint( "   - zużycie materiałów", inter.getVariable( "402_1")))
                    .append( toPrint( "   - usługi obce", inter.getVariable( "403_1")))
                    .append( toPrint( "   - wynagrodzenia", inter.getVariable( "404")))
                    .append( toPrint( "   - narzuty na płace", inter.getVariable( "405")))
                    .append( toPrint( "   - pozostałe", inter.getVariable( "765")))
                    .append( toPrint( "   - finansowe", inter.getVariable( "755_1")))
                    .append( toPrint( "C. Wynik", inter.getVariable( "wynik")))
                    .append( toPrint( "   - podatek", inter.getVariable( "podatek")))
                    .append( toPrint( "   + przychody NP", inter.getVariable( "750_2")))
                    .append( toPrint( "   - koszty NUP", inter.getVariable( "koszty_NUP")))
                    .append( toPrint( "X. Zysk/Strata", inter.getVariable( "zysk_strata")));

        }).orElseThrow();


    }

    private String toPrint( String label){
        return String.format( "%-50s\n");
    }

    private String toPrint( String label, BigDecimal value){
        return String.format( "%-30s %20s\n", label, DWA_MIEJSCA.format( value ));
    }

}
