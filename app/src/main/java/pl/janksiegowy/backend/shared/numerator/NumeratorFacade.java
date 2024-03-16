package pl.janksiegowy.backend.shared.numerator;

import lombok.AllArgsConstructor;
import pl.janksiegowy.backend.shared.numerator.dto.NumeratorDto;

import java.time.LocalDate;
import java.util.Optional;

@AllArgsConstructor
public class NumeratorFacade {

    private final NumeratorRepository numerators;
    private final NumeratorFactory factory;

    private final CounterRepository counters;
    public Numerator save( NumeratorDto source) {
        return numerators.save( Optional.ofNullable( source.getNumeratorId())
                            .map( uuid-> factory.update( source) )
                            .orElse( factory.from( source)));
    }

    public String increment( NumeratorCode code, String type, LocalDate... date) {
        StringBuffer result= new StringBuffer();

        counters.save( numerators.findByCode( code).map( numerator->
            numerator.getType().accept( new NumeratorType.NumeratorTypeVisitor<Counter>() {
                @Override public Counter visitYearNumerator() {
                    return counters.findByNumeratorCodeAndYearAndType( code, date[0].getYear(),
                                    numerator.isTyped()? type: "")
                            .orElse( new Counter()
                                    .setNumerator( numerator)
                                    .setMonth( -1)
                                    .setYear( date[0].getYear())
                                    .setType( numerator.isTyped()? type: ""));
                }
                @Override public Counter visitMonthNumerator() {
                    return counters.findByNumeratorCodeAndMonthAndType( code, date[0].getMonthValue(),
                                    numerator.isTyped()? type: "")
                            .orElse( new Counter()
                                    .setNumerator( numerator)
                                    .setMonth( date[0].getMonthValue())
                                    .setYear( date[0].getYear())
                                    .setType( numerator.isTyped()? type: ""));
                }
                @Override public Counter visitEverNumerator() {
                    return counters.findByNumeratorCodeAndType( code, numerator.isTyped()? type: "")
                            .orElse( new Counter()
                                    .setNumerator( numerator)
                                    .setMonth( -1)
                                    .setYear( -1)
                                    .setType( numerator.isTyped()? type: ""));
                }
            }).increment( result)).orElseThrow());
        return result.toString();
    }
}
