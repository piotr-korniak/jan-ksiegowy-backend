package pl.janksiegowy.backend.shared.numerator;

import pl.janksiegowy.backend.shared.numerator.dto.NumeratorDto;

import java.util.Optional;

public class NumeratorFactory {

    public Numerator from( NumeratorDto source) {
        return update( source, new Numerator());
    }

    public Numerator update( NumeratorDto source ) {
        return update( source, new Numerator().setNumeratorId( source.getNumeratorId()));
    }

    private Numerator update( NumeratorDto source, Numerator numerator) {
        return numerator
                .setCode( source.getCode())
                .setName( source.getName())
                .setMask( source.getMask())
                .setType( source.getType())
                .setTyped( source.isTyped());
    }


}
