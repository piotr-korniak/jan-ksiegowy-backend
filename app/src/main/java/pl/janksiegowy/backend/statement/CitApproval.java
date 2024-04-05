package pl.janksiegowy.backend.statement;

import lombok.AllArgsConstructor;
import pl.gov.crd.wzor._2024._02._08._13272.Deklaracja_CIT_8_33_v2_0e;
import pl.janksiegowy.backend.accounting.decree.DecreeLineQueryRepository;
import pl.janksiegowy.backend.metric.MetricRepository;
import pl.janksiegowy.backend.period.AnnualPeriod;
import pl.janksiegowy.backend.period.Period;
import pl.janksiegowy.backend.period.PeriodRepository;
import pl.janksiegowy.backend.period.PeriodType;
import pl.janksiegowy.backend.shared.Util;
import pl.janksiegowy.backend.shared.interpreter.DecreeLineFunction;
import pl.janksiegowy.backend.shared.interpreter.Interpreter;
import pl.janksiegowy.backend.shared.pattern.PatternId;
import pl.janksiegowy.backend.shared.pattern.XmlConverter;
import pl.janksiegowy.backend.statement.dto.StatementDto;

import javax.xml.datatype.DatatypeConfigurationException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDate;

@AllArgsConstructor
public class CitApproval {

    private final DecreeLineQueryRepository lines;
    private final PeriodRepository periods;
    private final MetricRepository metrics;
    private final StatementFacade statement;

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
                    .interpret( "wynik", "[przychody]- [koszty]")
                    .setVariable( "JEDEN", BigDecimal.ONE)
                    .interpret( "podstawa", "[wynik]@ [JEDEN]" )
                    .interpret( "podatek", "[podstawa]@ [podatek]")
                    .setVariable( "750_2", lines.turnoverDt( period, "750-2"))
                    .setVariable( "755_2", lines.turnoverDt( period, "755-2"))
                    .setVariable( "402_2", lines.turnoverDt( period, "402-2"))
                    .setVariable( "403_2", lines.turnoverDt( period, "403-2"))
                    .interpret( "koszty_NUP", "[755_2]+ [402_2]+ [403_2]")
                    .interpret( "zysk_strata", "[wynik]-[podatek]+ [750_2]- [koszty_NUP]" );

            if( PeriodType.A== period.getType()) {
                System.err.println( XmlConverter
                        .marshal( Factory_CIT_8.prepare( (AnnualPeriod)period, metrics, lines)));

                statement.save( StatementDto.create()
                        .type( StatementType.C)
                        .date( Util.min( LocalDate.now(), period.getEnd().plusMonths( 3)))
                        .liability( inter.getVariable( "podatek"))
                        .number( "CIT-8 "+ period.getBegin().getYear() +
                                ((period.getEnd().getYear()- period.getBegin().getYear())==1?
                                        period.getEnd().getYear()%100: ""))
                        .due( period.getEnd().plusMonths( 3))
                        .periodId( periodId));
            }

            return new StringBuilder()
                    .append( toPrint( "RZiS", period))
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
                    .append( toPrint( "   = podstawa", inter.getVariable( "podstawa")))
                    .append( toPrint( "   - podatek", inter.getVariable( "podatek")))
                    .append( toPrint( "   + przychody NP", inter.getVariable( "750_2")))
                    .append( toPrint( "   - koszty NUP", inter.getVariable( "koszty_NUP")))
                    .append( toPrint( "X. Zysk/Strata", inter.getVariable( "zysk_strata")));

        }).orElseThrow();


    }

    private String toPrint( String label, Period period){
        return String.format( "%-15s%10s - %10s\n", label,
                Util.toString( period.getBegin()), Util.toString( period.getEnd()));
    }

    private String toPrint( String label, BigDecimal value){
        return String.format( "%-23s%15s\n", label, DWA_MIEJSCA.format( value ));
    }

}
