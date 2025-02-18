package pl.janksiegowy.backend.finances.document;

import java.util.Optional;
import java.util.UUID;

public interface DocumentQueryRepository {
    Optional<Document> findByDocumentId( UUID documentId);
}
