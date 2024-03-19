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
                    .setVariable( "703", lines.turnoverCt( period, "703"))
                    .setVariable( "760", lines.turnoverCt( period, "760"))
                    .interpret( "przychody", "[703]+ [760]")
                    .setVariable( "4021", lines.turnoverDt( period,"402-1"))
                    .setVariable( "4031", lines.turnoverDt( period,"403-1"))
                    .interpret( "koszty", "[4021]+ [4031]");

            return new StringBuilder()
                    .append( toPrint( "A. Przychody", inter.getVariable( "przychody")))
                    .append( toPrint( "   - operacyjne", inter.getVariable( "703")))
                    .append( toPrint( "   - pozostałe", inter.getVariable( "760")))
                    .append( toPrint( "B. Koszty", inter.getVariable( "koszty" )))
                    .append( toPrint( "   - zużycie materiałów", inter.getVariable( "4021")))
                    .append( toPrint( "   - usługi obce", inter.getVariable( "4031")));
/*
            toPrint( "Sprzedaż", lines.turnoverCt( period, "703"));
            toPrint( "Materiały KUP", lines.turnoverDt( period,"402-1"));
            toPrint( "Materiały NUP", lines.turnoverDt( period,"402-2"));
            toPrint( "Usługi KUP", lines.turnoverDt( period,"403-1"));
            toPrint( "Usługi NUP", lines.turnoverDt( period,"403-2"));
            toPrint( "Koszty", inter.getVariable( "koszty" ));
*/
        }).orElseThrow();


    }

    private String toPrint( String label){
        return String.format( "%-50s\n");
    }

    private String toPrint( String label, BigDecimal value){
        return String.format( "%-30s %20s\n", label, DWA_MIEJSCA.format( value ));
    }

}
