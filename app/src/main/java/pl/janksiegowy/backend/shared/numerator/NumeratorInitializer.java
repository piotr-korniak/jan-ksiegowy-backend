package pl.janksiegowy.backend.shared.numerator;

import lombok.AllArgsConstructor;
import pl.janksiegowy.backend.shared.numerator.dto.NumeratorDto;

import java.time.LocalDate;

@AllArgsConstructor
public class NumeratorInitializer {

    private final NumeratorQueryRepository numerators;
    private final NumeratorFacade facade;

    public void init( NumeratorDto[] initialNumerators) {
        for( NumeratorDto numerator: initialNumerators){

            if( !numerators.existsByCode( numerator.getCode())){
                facade.save( numerator);
            }

        }
    }
}
