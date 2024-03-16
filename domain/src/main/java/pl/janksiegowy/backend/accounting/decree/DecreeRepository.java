package pl.janksiegowy.backend.accounting.decree;

import java.util.Optional;
import java.util.UUID;

public interface DecreeRepository {

    Decree save( Decree decree);

    Optional<Decree> findById( UUID id);
}
