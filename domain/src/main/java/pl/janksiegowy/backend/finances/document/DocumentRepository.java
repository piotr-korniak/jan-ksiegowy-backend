package pl.janksiegowy.backend.finances.document;

import java.util.Optional;
import java.util.UUID;

public interface DocumentRepository {

    Optional<Document> findByDocumentId( UUID documentId);
    void delete( Document document);
}
