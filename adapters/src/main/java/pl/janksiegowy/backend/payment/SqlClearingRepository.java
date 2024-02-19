package pl.janksiegowy.backend.payment;

import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SqlClearingRepository extends JpaRepository<Clearing, ClearingId> {
}

@org.springframework.stereotype.Repository
@AllArgsConstructor
class ClearingRepositoryImpl implements ClearingRepository {

    private final SqlClearingRepository repository;

    @Override public Clearing save( Clearing clearing) {
        return repository.save( clearing);
    }
}
