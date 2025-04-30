package pl.janksiegowy.backend.shared.numerator;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import pl.janksiegowy.backend.shared.MigrationService;
import pl.janksiegowy.backend.shared.numerator.dto.NumeratorDto;

import java.time.LocalDate;
import java.util.Optional;
@Log4j2

@AllArgsConstructor
public class NumeratorFacade {

    private final NumeratorRepository numerators;
    private final NumeratorFactory factory;
    private final MigrationService migrationService;
    private final CounterRepository counters;

    public Numerator save( NumeratorDto source) {
        return numerators.save( factory.from( source));
    }

    public String increment( NumeratorCode code, LocalDate... date) {
        return increment( code, "", date);
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
                    return counters.findByNumeratorCodeAndYearAndMonthAndType( code, date[0].getYear(),
                                    date[0].getMonthValue(), numerator.isTyped()? type: "")
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

    public String migrate() {
        int[] counters= { 0, 0};

        migrationService.loadNumerators().forEach(numerator-> {
            counters[0]++;

            numerators.findByCode( numerator.getCode()).orElseGet(()-> {
                counters[1]++;
                return save( numerator);
            });
        });
        log.warn( "Numerators migration complete!");
        return "%-50s %13s".formatted( "Numerators migration complete, added: ", counters[1]+ "/"+ counters[0]);
    }
}
