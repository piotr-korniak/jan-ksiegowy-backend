package pl.janksiegowy.backend.shared.numerator;

import pl.janksiegowy.backend.shared.numerator.dto.NumeratorDto;

import java.util.Optional;

public class NumeratorFactory {

    public Numerator from( NumeratorDto source) {
        return Optional.ofNullable( source.getId())
                .map( id-> update( new Numerator().setId( id), source))
                .orElse( update( new Numerator(), source));
    }

    private Numerator update( Numerator numerator, NumeratorDto source) {
        return numerator
                .setCode( source.getCode())
                .setName( source.getName())
                .setMask( source.getMask())
                .setType( source.getType());
    }
}
