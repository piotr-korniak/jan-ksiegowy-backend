package pl.janksiegowy.backend.accounting.decree;

import pl.janksiegowy.backend.finances.settlement.Settlement;

import java.util.Optional;
import java.util.UUID;

public interface DecreeRepository {

    Decree save( Decree decree);
    void delete( Decree decree);

    Optional<Decree> findById( UUID id);

}
