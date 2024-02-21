package pl.janksiegowy.backend.shared.numerator;

import lombok.AllArgsConstructor;
import pl.janksiegowy.backend.shared.numerator.dto.NumeratorDto;

import java.time.LocalDate;

@AllArgsConstructor
public class NumeratorFacade {

    private final NumeratorRepository numerators;
    private final NumeratorFactory factory;

    private final CounterRepository counters;
    public Numerator save( NumeratorDto numerator) {
        return numerators.save( factory.from( numerator));
    }

    public String increment( String numeratorCode, LocalDate date, String type) {
        StringBuffer result= new StringBuffer();

        counters.save( numerators.findByCode( numeratorCode).map( numerator->
            numerator.getType().accept( new NumeratorType.NumeratorTypeVisitor<Counter>() {
                @Override public Counter visitYearNumerator() {
                    return counters.findByNumeratorCodeAndYearAndType( numeratorCode, date.getYear(),
                                    numerator.isTypeUsed()? type: "")
                            .orElse( new Counter()
                                    .setNumerator( numerator)
                                    .setMonth( -1)
                                    .setYear( date.getYear())
                                    .setType( numerator.isTypeUsed()? type: ""));
                }
                @Override public Counter visitMonthNumerator() {
                    return counters.findByNumeratorCodeAndMonthAndType( numeratorCode, date.getMonthValue(),
                                    numerator.isTypeUsed()? type: "")
                            .orElse( new Counter()
                                    .setNumerator( numerator)
                                    .setMonth( date.getMonthValue())
                                    .setYear( date.getYear())
                                    .setType( numerator.isTypeUsed()? type: ""));
                }
                @Override public Counter visitEverNumerator() {
                    return counters.findByNumeratorCodeAndType( numeratorCode, numerator.isTypeUsed()? type: "")
                            .orElse( new Counter()
                                    .setNumerator( numerator)
                                    .setMonth( -1)
                                    .setYear( -1)
                                    .setType( numerator.isTypeUsed()? type: ""));
                }
            }).increment( result)).orElseThrow());
        return result.toString();
    }
}
