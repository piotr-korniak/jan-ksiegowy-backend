package pl.janksiegowy.backend.statement.formatter;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.gov.crd.wzor._2021._12._27._11149.Ewidencja_JPK_V7K_2_1_0e;
import pl.gov.crd.wzor._2021._12._27._11149.TKodFormularza;
import pl.janksiegowy.backend.metric.MetricRepository;
import pl.janksiegowy.backend.period.Period;
import pl.janksiegowy.backend.shared.Util;
import pl.janksiegowy.backend.shared.interpreter.Interpreter;
import pl.janksiegowy.backend.shared.pattern.PatternId;
import pl.janksiegowy.backend.shared.pattern.XmlConverter;
import pl.janksiegowy.backend.tax.TaxType;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
@AllArgsConstructor
public class Formatter_JPK_V7K_2_1_0e implements TaxDeclarationFormatter {

    private final MetricRepository metrics;
    private final LocalDate dateApplicable= LocalDate.of( 2020, 1, 1);

    @Override public String format( Period period, Interpreter result) {
        return XmlConverter.marshal( prepare( period, result));
    }

    @Override
    public PatternId getPatternId() {
        return  PatternId.JPK_V7K_2_1_0e;
    }

    private Ewidencja_JPK_V7K_2_1_0e prepare( Period period, Interpreter result) {
        var ewidencja= new Ewidencja_JPK_V7K_2_1_0e();

        metrics.findByDate( period.getBegin())
            .ifPresent( metric -> {
                ewidencja.setNaglowek( new Ewidencja_JPK_V7K_2_1_0e.Naglowek() {{
                    wariantFormularza= 2;
                    setKodFormularza( new KodFormularza(){{
                        value= TKodFormularza.JPK_VAT;
                        kodSystemowy= getKodSystemowy();
                        wersjaSchemy= getWersjaSchemy();
                    }});

                    setDataWytworzeniaJPK( Util.gregorianNow());
                    setCelZlozenia( new CelZlozenia() {{
                        value= (byte) (result.getVariable( "POWOD", BigDecimal.ONE)
                                .subtract( BigDecimal.ONE).signum()== 0? 1:2);
                        setPoz( getPoz());
                    }} );

                    setKodUrzedu( metric.getRcCode());
                    setRok( Util.toGregorianYear( period.getBegin()));
                    setMiesiac( (byte)period.getBegin().getMonthValue());

                }});


            });



        return ewidencja;
    }

    @Override public boolean isApplicable( TaxType taxType) {
        return taxType== TaxType.VQ;
    }

    @Override public LocalDate getDateApplicable() {
        return dateApplicable;
    }
}
