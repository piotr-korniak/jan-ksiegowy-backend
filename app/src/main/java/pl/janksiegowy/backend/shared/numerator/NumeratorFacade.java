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

        return numerators.findByCode( numeratorCode).map( numerator->
                numerator.getMask().contains( "T")?
                        increment( numerator, date, numeratorCode, type):
                        increment( numerator, date, numeratorCode)
        ).orElseThrow();
    }

    private String increment( Numerator numerator, LocalDate date, String code) {
        var counter= numerator.getType().accept( new NumeratorType.NumeratorTypeVisitor<Counter>() {
            @Override public Counter visitYearNumerator() {
                return counters.findByNumeratorCodeAndYear( code, date.getYear())
                        .orElse( new Counter()
                                .setNumerator( numerator)
                                .setMonth( -1)
                                .setYear( date.getYear()));
                        }
            @Override public Counter visitMonthNumerator() {
                return counters.findByNumeratorCodeAndMonth( code, date.getMonthValue())
                        .orElse( new Counter()
                                .setNumerator( numerator)
                                .setMonth( date.getMonthValue())
                                .setYear( date.getYear()));
            }
            @Override public Counter visitEverNumerator() {
                return counters.findByNumeratorCode( code)
                        .orElse( new Counter()
                                .setNumerator( numerator)
                                .setMonth( -1)
                                .setYear( -1));
                        }
                });

        var result= counter.increment();
        counters.save( counter);

        return result;
    }

    private String increment( Numerator numerator, LocalDate date, String code, String type) {
        var counter= numerator.getType().accept( new NumeratorType.NumeratorTypeVisitor<Counter>() {
            @Override public Counter visitYearNumerator() {
                return counters.findByNumeratorCodeAndYearAndType( code, date.getYear(), type)
                        .orElse( new Counter()
                                .setNumerator( numerator)
                                .setMonth( -1)
                                .setYear( date.getYear())
                                .setType( type));
            }
            @Override public Counter visitMonthNumerator() {
                return counters.findByNumeratorCodeAndMonthAndType( code, date.getMonthValue(), type)
                        .orElse( new Counter()
                                .setNumerator( numerator)
                                .setMonth( date.getMonthValue())
                                .setYear( date.getYear())
                                .setType( type));
            }
            @Override public Counter visitEverNumerator() {
                return counters.findByNumeratorCodeAndType( code, type)
                        .orElse( new Counter()
                                .setNumerator( numerator)
                                .setMonth( -1)
                                .setYear( -1)
                                .setType( type));
            }
        });

        var result= counter.increment();
        counters.save( counter);

        return result;
    }
}
