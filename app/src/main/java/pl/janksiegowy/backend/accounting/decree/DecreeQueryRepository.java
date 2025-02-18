package pl.janksiegowy.backend.accounting.decree;

import pl.janksiegowy.backend.accounting.decree.dto.DecreeDto;

import java.util.Optional;

public interface DecreeQueryRepository {

    Optional<DecreeDto> findByDocument( final String name);
}
