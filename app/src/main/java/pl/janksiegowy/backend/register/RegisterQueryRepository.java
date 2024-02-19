package pl.janksiegowy.backend.register;

import pl.janksiegowy.backend.register.dto.RegisterDto;

import java.util.Optional;

public interface RegisterQueryRepository {
    Optional<RegisterDto> findByCode( String code);
    boolean existsByCode( String code);
}
