package pl.janksiegowy.backend.declaration;

import pl.janksiegowy.backend.accounting.decree.DecreeLineQueryRepository;
import pl.janksiegowy.backend.metric.Metric;
import pl.janksiegowy.backend.period.AnnualPeriod;
import pl.janksiegowy.backend.shared.Util;
import pl.janksiegowy.backend.shared.interpreter.Interpreter;
import pl.janksiegowy.backend.shared.pattern.PatternId;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

public abstract class Factory_CIT_8 {
    protected AnnualPeriod period;
    protected Interpreter data;

    protected byte reason= 1;

    public static Factory_CIT_8 create( AnnualPeriod period, DecreeLineQueryRepository lines) {

        return getPatternId( Util.min( LocalDate.now(), period.getEnd().plusMonths( 3)))
                .map( patternId -> patternId.accept( new PatternId.PatternCitVisitor<Factory_CIT_8>() {
                    @Override public Factory_CIT_8 visit_CIT_8_33_v2_0e() {
                        return ((Factory_CIT_8) new Factory_CIT_8_33_v2_0e()).prepare( period, lines);
                    }
                })).orElseThrow();
    }

    private Factory_CIT_8 prepare( AnnualPeriod period, DecreeLineQueryRepository lines) {
        this.period= period;
        this.data= new Interpreter()
                .setVariable( "podatek", new BigDecimal( "0.09"))
                .setVariable( "703", lines.turnoverCt( period, "703"))
                .setVariable( "760", lines.turnoverCt( period, "760"))
                .setVariable( "750_1", lines.turnoverDt( period, "750-1"))
                .interpret( "przychody", "[703]+ [760]+ [750_1]")
                .setVariable( "402_1", lines.turnoverDt( period,"402-1"))
                .setVariable( "403_1", lines.turnoverDt( period,"403-1"))
                .setVariable( "404", lines.turnoverDt( period, "404"))
                .setVariable( "405", lines.turnoverDt( period, "405"))
                .setVariable( "409_1", lines.turnoverDt( period, "409-1"))
                .setVariable( "755_1", lines.turnoverDt( period, "755-1"))
                .setVariable( "765", lines.turnoverCt( period, "765"))
                .interpret( "koszty", "[402_1]+ [403_1]+ [404]+ [405]+ [409_1]+ [755_1]+ [765]")
                .interpret( "wynik", "[przychody]- [koszty]")
                .setVariable( "JEDEN", BigDecimal.ONE)
                .interpret( "podstawa", "[wynik]@ [JEDEN]" );
        // podatek, jeśli podstawa > 0
        data.setVariable( "podstawa", data.getVariable("podstawa").max( BigDecimal.ZERO))
                .interpret( "podatek", "[podstawa]* [podatek]");
        System.err.println( "Podstawa: " + data.getVariable("podstawa"));
        System.err.println( "Podatek: " + data.getVariable("podatek"));
        return this;
    }


    protected abstract Statement_CIT_8 prepare( Metric metric);


    private static Optional<PatternId> getPatternId( LocalDate date ) {
        return Optional.of( PatternId.CIT_8_33_v2_0e);
    }

    protected int setReason( int reason) {
        this.reason= (byte) (reason==1? 1: 2);
        return reason;
    }
}
