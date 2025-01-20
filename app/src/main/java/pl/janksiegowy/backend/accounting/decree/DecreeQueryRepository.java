package pl.janksiegowy.backend.accounting.decree;

import java.util.UUID;

public interface DecreeQueryRepository {

    boolean existsByDocument( String document);
    Decree findById( UUID decreeId);
}
