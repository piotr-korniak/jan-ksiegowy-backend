package pl.janksiegowy.backend.shared.update;

import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SqlUpdateRepository extends JpaRepository<Update, Long> {
    public boolean existsByStepUrl( String stepUrl);
}

@org.springframework.stereotype.Repository
@AllArgsConstructor
class UpdateRepositoryImpl implements UpdateRepository {

    private final SqlUpdateRepository updateRepository;

    @Override public boolean existsByStepUrl( String stepUrl) {
        return updateRepository.existsByStepUrl( stepUrl);
    }

    @Override public Update save(Update update) {
        return updateRepository.save( update);
    }
}
