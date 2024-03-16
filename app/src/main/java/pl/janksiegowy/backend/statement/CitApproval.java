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

    public void approval( String periodId) {

        periods.findById( periodId).ifPresent( period-> {

            var inter =new Interpreter()
                    .setVariable( "402", lines.turnoverDt( period,"402-1"))
                    .setVariable( "403", lines.turnoverDt( period,"403-1"))
                    .interpret( "koszty", "[402]+ [403]");

            toPrint( "Sprzedaż", lines.turnoverCt( period, "703"));
            toPrint( "Materiały KUP", lines.turnoverDt( period,"402-1"));
            toPrint( "Materiały NUP", lines.turnoverDt( period,"402-2"));
            toPrint( "Usługi KUP", lines.turnoverDt( period,"403-1"));
            toPrint( "Usługi NUP", lines.turnoverDt( period,"403-2"));
            toPrint( "Koszty", inter.getVariable( "koszty" ));

        });


    }

    private void toPrint( String label, BigDecimal value){
        System.err.println( String.format( "%-30s %21s ", label, DWA_MIEJSCA.format( value )));
    }

}
