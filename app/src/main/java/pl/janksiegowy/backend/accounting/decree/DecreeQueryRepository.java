package pl.janksiegowy.backend.accounting.decree;

import pl.janksiegowy.backend.accounting.decree.dto.DecreeDto;

import java.util.Optional;
import java.util.UUID;

public interface DecreeQueryRepository {

    Optional<DecreeDto> findByDocument( final String name);
    Optional<DecreeDto> findProjectedByDecreeId(final UUID degreeId);
}
