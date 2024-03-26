package pl.janksiegowy.backend.finances.payment;

import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.janksiegowy.backend.finances.clearing.Clearing;
import pl.janksiegowy.backend.finances.clearing.ClearingId;
import pl.janksiegowy.backend.finances.clearing.ClearingRepository;
import pl.janksiegowy.backend.finances.settlement.Settlement;

import java.util.List;
import java.util.stream.Stream;

public interface SqlClearingRepository extends JpaRepository<Clearing, ClearingId> {
    List<Clearing> findByReceivable( Settlement settlement);

    List<Clearing> findByPayable( Settlement settlement);
}

@org.springframework.stereotype.Repository
@AllArgsConstructor
class ClearingRepositoryImpl implements ClearingRepository {

    private final SqlClearingRepository repository;

    @Override public Clearing save( Clearing clearing) {
        return repository.save( clearing);
    }

    @Override public List<Clearing> receivable( Settlement settlement ) {
        return repository.findByReceivable( settlement);
    }

    @Override public List<Clearing> payable( Settlement settlement ) {
        return repository.findByPayable( settlement);
    }
}
