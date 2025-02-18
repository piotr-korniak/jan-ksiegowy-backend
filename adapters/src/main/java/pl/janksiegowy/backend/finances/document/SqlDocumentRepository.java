package pl.janksiegowy.backend.finances.document;

import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;
import pl.janksiegowy.backend.finances.clearing.Clearing;
import pl.janksiegowy.backend.finances.clearing.ClearingId;
import pl.janksiegowy.backend.finances.clearing.ClearingQueryRepository;
import pl.janksiegowy.backend.finances.clearing.ClearingRepository;

import java.util.Optional;
import java.util.UUID;

public interface SqlDocumentRepository extends JpaRepository<Document, UUID> {
}


interface SqlDocumentQueryRepository extends DocumentQueryRepository, Repository<Document, UUID> {

}

@org.springframework.stereotype.Repository
@AllArgsConstructor
class DocumentRepositoryImpl implements DocumentRepository {

    private final SqlDocumentRepository documentRepository;

    @Override public Optional<Document> findByDocumentId( UUID documentId) {
        return documentRepository.findById( documentId);
    }

    @Override public void delete( Document document) {
        documentRepository.delete( document);
    }
}


