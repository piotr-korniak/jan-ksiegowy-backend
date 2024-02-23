package pl.janksiegowy.backend.finances.payment;

import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.janksiegowy.backend.finances.clearing.Clearing;
import pl.janksiegowy.backend.finances.clearing.ClearingId;
import pl.janksiegowy.backend.finances.clearing.ClearingRepository;

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
