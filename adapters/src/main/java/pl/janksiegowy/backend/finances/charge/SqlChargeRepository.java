package pl.janksiegowy.backend.finances.charge;

import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SqlChargeRepository extends JpaRepository<Charge, UUID> {
}

@org.springframework.stereotype.Repository
@AllArgsConstructor
class ChargeRepositoryImpl implements ChargeRepository {

    private final SqlChargeRepository repository;

    @Override public Charge save( Charge charge) {
        return repository.save( charge);
    }
}
