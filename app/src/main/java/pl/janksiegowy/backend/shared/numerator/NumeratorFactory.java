package pl.janksiegowy.backend.shared.numerator;

import pl.janksiegowy.backend.shared.numerator.dto.NumeratorDto;

import java.util.Optional;
import java.util.UUID;

public class NumeratorFactory {

    public Numerator from( NumeratorDto source) {
        return new Numerator()
                .setNumeratorId( Optional.ofNullable( source.getNumeratorId()).orElseGet(UUID::randomUUID))
                .setCode( source.getCode())
                .setName( source.getName())
                .setMask( source.getMask())
                .setType( source.getType())
                .setTyped( source.isTyped());
    }
}
